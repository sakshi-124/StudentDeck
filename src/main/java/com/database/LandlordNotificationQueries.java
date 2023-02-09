package com.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.baseDesignPatterns.QueriesTemplate;
import com.model.LandlordNotification;

public class LandlordNotificationQueries extends QueriesTemplate {
	static Connection conn;

	public LandlordNotificationQueries() {
		conn = Database.getConnection();
	}

	public void writeDb(Object obj, List<String> messages) {
		LandlordNotification notification = (LandlordNotification) obj;
		
		String queryString = "INSERT INTO landlord_notification (admin_id, landlord_id, notification, source) "
				+ "VALUES (?,?,?,?)";
		try {
			PreparedStatement query = conn.prepareStatement(queryString);
			query.setLong(1, notification.getSourceId());						
			query.setLong(2, notification.getLandlordId());
			query.setString(3, notification.getNotification());
			query.setString(4, notification.getSource());
			
            query.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			messages.add("Error Occured!");
			messages.add("Please try again");
		}
		
	}

	public List<String> fetchByLandlordId(List<Object> notifications, int landlordId) {
		List<String> messages = new ArrayList<String>();
		String queryString = "SELECT * FROM landlord_notification WHERE landlord_id=?";
		try {
			PreparedStatement query = conn.prepareStatement(queryString);
			query.setInt(1, landlordId);
            ResultSet rs = query.executeQuery();
            while(rs.next()) {
            	LandlordNotification notification = new LandlordNotification();
            	notification.setLandlordId(Long.parseLong(rs.getString("landlord_id")));
            	notification.setNotification(rs.getString("notification"));
            	notification.setSource(rs.getString("source"));
            	notifications.add(notification);
            }
		} catch (SQLException e) {
			e.printStackTrace();
			messages.add("Error Occured!");
			messages.add("Please try again");
		}
		
		return messages;	
	}
}
