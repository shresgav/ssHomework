package library.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Utility {
	private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/library";
	private static final String username = "root";
	private static final String password = "root";
	
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, username, password);
		conn.setAutoCommit(Boolean.FALSE);
		return conn;
	}

}
