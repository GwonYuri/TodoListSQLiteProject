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
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 추가]\n"
				+ "제목 > ");
		title = sc.next(); // 제목은 한단어 입력받음
		if (list.isDuplicate(title)) {
			System.out.println("제목이 중복됩니다.");
			return;
		} // 제목 중복 시 항목 추가하지 않고 종료
		
		sc.nextLine();
		System.out.print("내용 > ");
		desc = sc.nextLine().trim(); // 내용은 한문장 전체를 입력받음
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("항목이 추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 삭제]\n"
				+ "삭제할 항목 제목 > ");
		String title = sc.next();
		if (!l.isDuplicate(title)) {
			System.out.println("없는 제목입니다.");
			return;
		} // 없는 항목을 삭제할 시 삭제하지 않고 종료
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("항목이 삭제되었습니다.");
				break;
			}
		}
	}

	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 수정]\n"
				+ "수정할 항목 제목 > ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("없는 제목입니다.");
			return;
		} // 없는 항목을 수정할 시 수정하지 않고 종료

		System.out.print("새로운 제목 > ");
		String new_title = sc.next().trim(); // 제목은 한단어 입력받음
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됩니다.");
			return;
		}
		
		sc.nextLine(); 
		System.out.print("새로운 내용 > ");
		String new_description = sc.nextLine().trim(); // 내용은 한문장 전체를 입력받음
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("항목이 수정되었습니다.");
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
		catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	public static void loadList(TodoList l, String filename) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(filename));
				
				String oneline;
				int count = 0;
				while((oneline = br.readLine()) != null) {
					StringTokenizer st = new StringTokenizer(oneline, "##");
					String title = st.nextToken();
					String desc = st.nextToken();
					String date = st.nextToken();
					
					TodoItem i = new TodoItem(title, desc, date);
					l.addItem(i);
					count++;
				}
				br.close();
				System.out.println(count + " 개의 항목을 읽었습니다.\n");
			}
			catch(FileNotFoundException e) {
				System.out.println("todolist.txt 파일이 없습니다.");
			}catch(IOException e) {
				e.printStackTrace();
			}
	}

	public static void listAll(TodoList l) {
		System.out.println("[전체 목록]");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
}
