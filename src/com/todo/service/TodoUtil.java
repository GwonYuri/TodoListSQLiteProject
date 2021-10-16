package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String category, title, desc, due_date, is_completed, is_lated, is_important;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 추가]\n" + "카테고리 > ");
		category = sc.next();
		
		System.out.print("제목 > ");
		title = sc.next(); // 제목은 한단어 입력받음
		if (list.isDuplicate(title)) {
			System.out.println("제목이 중복됩니다.");
			return;
		} // 제목 중복 시 항목 추가하지 않고 종료
		
		sc.nextLine();
		System.out.print("내용 > ");
		desc = sc.nextLine().trim(); // 내용은 한문장 전체를 입력받음
		
		System.out.print("마감일 > ");
		due_date = sc.nextLine().trim(); 
		
		System.out.print("완료 여부(0: 미완료, 1: 완료) > ");
		is_completed = sc.next();
		
		System.out.print("지각 여부(0: 지각 아님, 1: 지각함) > ");
		is_lated = sc.next();
		
		System.out.print("중요성 여부(0: 중요하지 않음, 1: 중요함) > ");
		is_important = sc.next();
		
		TodoItem t = new TodoItem(title, desc, category, due_date, is_completed, is_lated, is_important);
		if(list.addItem(t) > 0) {
			System.out.println("항목이 추가되었습니다.");
		}
	}

	public static void updateItem(TodoList l) {
		
		int upd_num;
		String new_category, new_title, new_description, new_duedate, is_completed, is_lated, is_important;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 수정]\n" + "수정할 항목 번호 > ");
		upd_num = sc.nextInt();

		System.out.print("카테고리 > ");
		new_category = sc.next().trim();
		
		System.out.print("새로운 제목 > ");
		new_title = sc.next().trim(); // 제목은 한단어 입력받음
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됩니다.");
			return;
		}
		
		sc.nextLine(); 
		System.out.print("새로운 내용 > ");
		new_description = sc.nextLine().trim(); // 내용은 한문장 전체를 입력받음
		
		System.out.print("새로운 마감일 > ");
		new_duedate = sc.nextLine().trim(); 
		
		System.out.print("완료 여부(0: 미완료, 1: 완료) > ");
		is_completed = sc.next();
		
		System.out.print("지각 여부(0: 지각 아님, 1: 지각함) > ");
		is_lated = sc.next();
		
		System.out.print("중요성 여부(0: 중요하지 않음, 1: 중요함) > ");
		is_important = sc.next();
		
		TodoItem t = new TodoItem(new_title, new_description, new_category, new_duedate, is_completed, is_lated, is_important);
		t.setId(upd_num);
		if(l.updateItem(t) > 0)
			System.out.println("항목이 수정되었습니다.");
		
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 삭제]\n" + "삭제할 항목 번호 > ");
		int del_num = sc.nextInt();
		
		if(l.deleteItem(del_num) > 0)
			System.out.println("항목이 삭제되었습니다.");
	}
	
	public static void saveList(TodoList l, String filename) {
		try {
			Writer w = new FileWriter(filename);
			for(TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
			System.out.println("모든 데이터가 저장되었습니다.");
		}
		catch(IOException e) {
			e.printStackTrace();
		}	
	}
	
	public static void loadList(TodoList l, String filename) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(filename));
				
				String line;
				int count = 0;
				while((line = br.readLine()) != null) {
					StringTokenizer st = new StringTokenizer(line, "##");
					String category = st.nextToken();
					String title = st.nextToken();
					String desc = st.nextToken();
					String due_date = st.nextToken();
					String current_date = st.nextToken();
					String is_completed = st.nextToken();
					String is_lated = st.nextToken();
					String is_important = st.nextToken();
					
					TodoItem i = new TodoItem(title, desc, category, due_date, is_completed, is_lated, is_important);
					l.addItem(i);
					count++;
				}
				br.close();
				System.out.println(count + " 개의 항목을 읽었습니다.\n");
			}
			catch(FileNotFoundException e) {
				System.out.println(filename+ " 파일이 없습니다.");
			}catch(IOException e) {
				e.printStackTrace();
			}
	}

	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록], 총 %d개\n", l.getCount());
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
 		}
 	}
	
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[전체 목록], 총 %d개\n", l.getCount());
		for(TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	
	public static void find(TodoList l, String keyword) {
		int count = 0;
		for(TodoItem i : l.getList(keyword)) {
			System.out.println(i.toString());
			count++;
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}
	
	public static void findByCategory(TodoList l, String cate_keyward) {
		int count = 0;
		for(TodoItem item : l.getListCategory(cate_keyward)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}
	
	public static void sortByCategory(TodoList l) {
		int count=0;
		
		for(String item : l.getCategories()) {
			System.out.print(item+" ");
			count++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n",count);
	}
	
	public static void completeItem(TodoList l, int number) {
		if(l.completeItem(number) > 0)
			System.out.println("완료된 항목으로 체크하였습니다.");
	}
	
	public static void completeListAll(TodoList l) {
		int count = 0;
		
		for(TodoItem i : l.getList()) {
			if(i.getIs_completed().equals("1")) {
				System.out.println(i.toString());
				count++;
			}
		}
		System.out.println("총 " + count + "개의 항목을 완료했습니다.");
	}
	
	public static void lateItem(TodoList l, int number) {
		if(l.lateItem(number) > 0)
			System.out.println("지각한 항목으로 체크하였습니다.");
	}
	
	public static void lateListAll(TodoList l) {
		int count = 0;
		
		for(TodoItem i : l.getList()) {
			if(i.getIs_lated().equals("1")) {
				System.out.println(i.toString());
				count++;
			}
		}
		System.out.println("총 " + count + "개의 항목이 지각했습니다.");
	}
	
	public static void importantItem(TodoList l, int number) {
		if(l.importantItem(number) > 0)
			System.out.println("중요한 항목으로 체크하였습니다.");
	}
	
	public static void importantListAll(TodoList l) {
		int count = 0;
		
		for(TodoItem i : l.getList()) {
			if(i.getIs_important().equals("1")) {
				System.out.println(i.toString());
				count++;
			}
		}
		System.out.println("총 " + count + "개의 항목이 중요합니다.");
	}
	
}
