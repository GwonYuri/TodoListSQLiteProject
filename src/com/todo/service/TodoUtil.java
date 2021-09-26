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
		
		String category, title, desc, due_date;
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
		
		TodoItem t = new TodoItem(category, title, desc, due_date);
		list.addItem(t);
		System.out.println("항목이 추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 삭제]\n" + "삭제할 항목 번호 > ");
		int del_num = sc.nextInt();
		
		for (TodoItem item : l.getList()) {
			if (del_num == l.indexOf(item)+1) {
				System.out.println(l.indexOf(item)+1 + "." + item.toString());
				l.deleteItem(item);
				System.out.println("항목이 삭제되었습니다.");
				break;
			}
		}
	}

	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 수정]\n" + "수정할 항목 번호 > ");
		int upd_num = sc.nextInt();

		System.out.print("카테고리 > ");
		String new_category = sc.next().trim();
		
		System.out.print("새로운 제목 > ");
		String new_title = sc.next().trim(); // 제목은 한단어 입력받음
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됩니다.");
			return;
		}
		
		sc.nextLine(); 
		System.out.print("새로운 내용 > ");
		String new_description = sc.nextLine().trim(); // 내용은 한문장 전체를 입력받음
		
		System.out.print("새로운 마감일 > ");
		String new_duedate = sc.nextLine().trim(); 
		
		for (TodoItem item : l.getList()) {
			if(upd_num == l.indexOf(item) + 1) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_category, new_title, new_description, new_duedate);
				l.addItem(t);
				System.out.println("항목이 수정되었습니다.");
				break;
			}
		}
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
					
					TodoItem i = new TodoItem(category, title, desc, due_date,current_date);
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
		System.out.println("[전체 목록], 총 " +l.getList().size()+"개]");
		for (TodoItem item : l.getList()) {
			System.out.println(l.indexOf(item)+1 + "." + item.toString());
		}
	}
	
	public static void find(TodoList l, String keyward) {
		int count = 0;
		for(TodoItem i : l.getList()) {
			if(i.getTitle().contains(keyward) || i.getDesc().contains(keyward)) {
				System.out.println(l.indexOf(i)+1 + "." + i.toString());
				count++;
			}
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}
	
	public static void findByCategory(TodoList l, String cate_keyward) {
		int count = 0;
		for(TodoItem i : l.getList()) {
			if(i.getCategory().contains(cate_keyward)) {
				System.out.println(l.indexOf(i)+1 + "." + i.toString());
				count++;
			}
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}
	
	public static void sortByCategory(TodoList l) {
		HashSet<String> cate = new HashSet<String>();

		for(TodoItem i : l.getList()) {
			cate.add(i.getCategory());
		}
		
		System.out.println(String.join(" / ", cate));
		System.out.println("총 " + cate.size() + "개의 카테고리가 등록되어 있습니다.");
	}
}
