package com.amazon.service.item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.amazon.domain.Conn;
import com.amazon.domain.Item;
import com.mysql.jdbc.PreparedStatement;

public class ItemService {
	PreparedStatement ps;
	Connection conn = Conn.startConnection();
	
	public List<Item> getItems() {
		
		List<Item> items = new ArrayList<>();
		
		String remove = "select * from item";
		try {
			ps = (PreparedStatement) conn.prepareStatement(remove);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Item item=new Item();
				item.id = rs.getInt("id");
				item.name = rs.getString("name");
				item.unitPrice=rs.getFloat("price");
				
			items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}

}
