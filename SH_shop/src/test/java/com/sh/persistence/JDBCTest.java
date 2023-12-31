package com.sh.persistence;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

public class JDBCTest {
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); //JDBC 드라이버 로딩
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConnection() {
		
		try(Connection con =                       //DB서버와의 연결작업
				DriverManager.getConnection(
						"jdbc:mysql://127.0.0.1:3307/book?serverTimezone=Asia/Seoul",
						"castello",
						"0000")){ 
			System.out.println(con);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
}
