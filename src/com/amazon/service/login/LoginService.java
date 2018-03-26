package com.amazon.service.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.amazon.domain.Conn;

public class LoginService {
	
	PreparedStatement ps;
	Connection conn = Conn.startConnection();
	
	

	public boolean authenticate(String username, String password) {
		try {
			PreparedStatement ps = (PreparedStatement) conn
					.prepareStatement("select * from login where username=? and password=?");
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs=ps.executeQuery();
			
			
			
			if(rs.next())
				return true;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		return false;
	}

}
