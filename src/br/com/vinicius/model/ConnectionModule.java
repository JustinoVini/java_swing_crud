package br.com.vinicius.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionModule {
	public static Connection conect() {
		Connection con;
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/";
		String user = "root";		
		String password = "@Viking569038";
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e);
			return null;
		}
	}
}
