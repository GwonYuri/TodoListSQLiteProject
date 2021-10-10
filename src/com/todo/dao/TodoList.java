package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	private List<TodoItem> list;
	Connection conn;
	
	public TodoList() {
		this.conn = DbConnect.getConnection();
		this.list = new ArrayList<TodoItem>();
	}

	//특정 파일 이름의 txt파일 읽어오기
	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (title, memo, category, current_date, due_date)" +"values (?,?,?,?,?);";
			
			int records = 0;
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, description);
				pstmt.setString(3, category);
				pstmt.setString(4, current_date);
				pstmt.setString(5, due_date);
				
				int count = pstmt.executeUpdate();
				if(count > 0) records++;
				pstmt.close();
			}
			System.out.println(records + " records read!!");
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Create
	public int addItem(TodoItem t) {
		String sql = "insert into list (title, memo, category, current_date, due_date)" 
				+"values (?,?,?,?,?);";
		PreparedStatement pstmt;
		
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDueDate());
			
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(int index) {
		String sql = "delete from list where id=?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int updateItem(TodoItem t) {
		String sql = "update list set title=?, memo=?, category=?, current_date=?, due_date=?" + " where id = ?;";
		PreparedStatement pstmt;
		
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,t.getTitle());
			pstmt.setString(2,t.getDesc());
			pstmt.setString(3,t.getCategory());
			pstmt.setString(4,t.getCurrent_date());
			pstmt.setString(5,t.getDueDate());
			pstmt.setInt(6, t.getId());
			
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());
	}
	
	public void listAll() {
		System.out.println("\n===== 전체 목록 보기 =====");
		for (TodoItem myitem : list) {
			System.out.println(myitem.toString());
		}
	}
	
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String title) {
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}
	
	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				list.add(new TodoItem(rs.getInt("id"), rs.getString("category"), rs.getString("title"),
						rs.getString("memo"), rs.getString("due_date"), rs.getString("current_date")));
			}

			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int getCount() {
		Statement stmt;
		
		int count = 0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String category = rs.getString("category");
				list.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		
		try {
			String sql = "SELECT * FROM list WHERE category = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,keyword);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				list.add(new TodoItem(rs.getInt("id"), rs.getString("category"), rs.getString("title"),
						rs.getString("memo"), rs.getString("due_date"), rs.getString("current_date")));
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + orderby;
			if(ordering == 0)
				sql += " desc";
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				list.add(new TodoItem(rs.getInt("id"), rs.getString("category"), rs.getString("title"),
						rs.getString("memo"), rs.getString("due_date"), rs.getString("current_date")));
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
