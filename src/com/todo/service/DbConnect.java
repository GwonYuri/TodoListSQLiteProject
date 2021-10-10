package com.todo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {
	private static Connection conn = null;
	
	public static void closeConnection() {
		if(conn != null) {
			try {
			conn.close(); // Connection 객체 종료
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Connection getConnection() {
		if(conn == null) {
			try {
			Class.forName("org.sqlite.JDBC"); // SQLite JDBC 체크
			conn = DriverManager.getConnection("jdbc:sqlite:" + "todolist.db"); // SQLite db 파일에 연결
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return conn;
	}
}
