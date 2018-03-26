package com.amazon.domain;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn{
	
	public static java.sql.Connection startConnection() {
		try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException c) {
		c.printStackTrace();
	}
	java.sql.Connection connection = null;

	try {
		connection = DriverManager
		.getConnection("jdbc:mysql://localhost:3306/demo","root", "1234");

	} 
	catch (SQLException e) {
		e.printStackTrace();
	}
	return connection;
	}
}
