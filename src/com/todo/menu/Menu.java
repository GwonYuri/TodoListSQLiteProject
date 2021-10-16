package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("<TodoList 관리 명령어 사용법>");
        System.out.println("add - 항목 추가");
        System.out.println("edit - 항목 수정");
        System.out.println("del - 항목 삭제");
        System.out.println("ls - 전체 목록");
        System.out.println("ls_name - 제목순 정렬");
        System.out.println("ls_name_desc - 제목역순 정렬");
        System.out.println("ls_date - 날짜순 정렬");
        System.out.println("ls_date_desc - 날짜역순 정렬");
        System.out.println("ls_cate - 현재 등록된 카테고리 출력(중복 없음)");
        System.out.println("find <keyword> - 해당 키워드를 포함하고 있는 모든 항목 출력");
        System.out.println("find_cate <keyword> - 해당 카테고리 키워드를 포함하고 있는 모든 항목 출력");
        System.out.println("comp - 완료된 항목으로 체크");
        System.out.println("ls_comp - 완료된 항목 목록 출력");
        System.out.println("exit - 종료");
    }
    
    public static void prompt() {
    	System.out.print("\nCommand > ");
    }
}
