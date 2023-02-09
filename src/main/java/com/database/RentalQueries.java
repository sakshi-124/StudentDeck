package com.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.baseDesignPatterns.QueriesTemplate;
import com.baseDesignPatterns.ResponseState;
import com.model.ErrorResponse;
import com.model.RentalList;
import com.model.SuccessResponse;

public class RentalQueries extends QueriesTemplate {
	static Connection conn;

	public RentalQueries() {
		conn = Database.getConnection();
	}

	public void writeDb(Object obj, List<String> messages) {
		RentalList rental = (RentalList) obj;
		
		String queryString = "INSERT INTO rental_list (landlord_id, title, listing_type, rent, availability, max_occupancy,"
				+ " description, city, address, country, status, enabled) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement query = conn.prepareStatement(queryString);
			
			query.setLong(1, rental.getLandlordId());
			query.setString(2, rental.getTitle());
			query.setString(3, rental.getListingType());
			query.setDouble(4, Double.parseDouble(rental.getRent()));
			query.setString(5, rental.getAvailability());
			query.setInt(6, Integer.parseInt(rental.getMaxOccupancy()));
			query.setString(7, rental.getDescription());
			query.setString(8, rental.getCity());
			query.setString(9, rental.getAddress());
			query.setString(10, rental.getCountry());
			query.setByte(11, (byte) 0);
			query.setByte(12, (byte) 1);
			
            query.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			messages.add("Error Occured!");
			messages.add("Please try again");
		}
		
	}

	public void update(Object obj, List<String> messages) {
		RentalList rental = (RentalList) obj;
		
		String queryString = "UPDATE rental_list set title=?, listing_type=?, rent=?, availability=?, max_occupancy=?,"
				+ " description=?, city=?, address=?, country=?, status=? WHERE id=? and landlord_id=?;";
		try {
			PreparedStatement query = conn.prepareStatement(queryString);
			
			
			query.setString(1, rental.getTitle());
			query.setString(2, rental.getListingType());
			query.setDouble(3, Double.parseDouble(rental.getRent()));
			query.setString(4, rental.getAvailability());
			query.setInt(5, Integer.parseInt(rental.getMaxOccupancy()));
			query.setString(6, rental.getDescription());
			query.setString(7, rental.getCity());
			query.setString(8, rental.getAddress());
			query.setString(9, rental.getCountry());
			query.setByte(10, (byte) 0);
			query.setLong(11, rental.getId());
			query.setLong(12, rental.getLandlordId()); 
			
            int count = query.executeUpdate();
            if(count != 1) {
    			messages.add("Error Occured!");
    			messages.add("Please try again");
            }
		} catch (SQLException e) {
			e.printStackTrace();
			messages.add("Error Occured!");
			messages.add("Please try again");
		}
		
	}

	public void delete(Object id, List<String> messages) {
		String queryString = "UPDATE rental_list SET enabled=? WHERE id=?";
		try {
			PreparedStatement query = conn.prepareStatement(queryString);
			query.setInt(1, (byte) 0);
			query.setInt(2, (Integer) id);
            query.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			messages.add("Error Occured!");
			messages.add("Please try again");
		}
	}

	public List<String> fetchByLandlordId(List<Object> rentals, int landlordId) {	
		List<String> messages = new ArrayList<String>();
		String queryString = "SELECT * FROM rental_list WHERE landlord_id=? and enabled=?";
		try {
			PreparedStatement query = conn.prepareStatement(queryString);
			query.setInt(1, landlordId);
			query.setInt(2, (byte) 1);
            ResultSet rs = query.executeQuery();
            while(rs.next()) {
            	RentalList rental = new RentalList();
            	rental.setId(Integer.parseInt(rs.getString("id")));
            	rental.setTitle(rs.getString("title"));
            	rental.setRent(rs.getString("rent"));
            	rental.setCity(rs.getString("city"));
            	rental.setCountry(rs.getString("country"));
            	rental.setAvailability(rs.getString("availability"));
            	rental.setListingType(rs.getString("listing_type"));
            	rental.setDescription(rs.getString("description"));
            	rental.setMaxOccupancy(rs.getString("max_occupancy"));
            	rental.setLandlordId(Long.parseLong(rs.getString("landlord_id")));
            	rental.setAddress(rs.getString("address"));
            	rentals.add(rental);
            }
		} catch (SQLException e) {
			e.printStackTrace();
			messages.add("Error Occured!");
			messages.add("Please try again");
		}
		
		return messages;	
	}

	public ResponseState fetchById(Object obj, List<String> messages) {
		int id = (int) obj;
		RentalList rental = new RentalList();
		ResponseState response = null;
		String queryString = "SELECT * FROM rental_list WHERE id=?";
		try {
			PreparedStatement query = conn.prepareStatement(queryString);
			query.setInt(1, id);
            ResultSet rs = query.executeQuery();
            while(rs.next()) {
            	rental.setId(Integer.parseInt(rs.getString("id")));
            	rental.setTitle(rs.getString("title"));
            	rental.setRent(rs.getString("rent"));
            	rental.setCity(rs.getString("city"));
            	rental.setCountry(rs.getString("country"));
            	rental.setAvailability(rs.getString("availability"));
            	rental.setListingType(rs.getString("listing_type"));
            	rental.setDescription(rs.getString("description"));
            	rental.setMaxOccupancy(rs.getString("max_occupancy"));
            	rental.setAddress(rs.getString("address"));
            	response = new SuccessResponse();
            	response.setMessages(messages);
            	response.setResponseObject(rental);
            	return response;
            }
		} catch (SQLException e) {
			e.printStackTrace();
			messages.add("Error Occured!");
			messages.add("Please try again");
			response = new ErrorResponse();
			response.setMessages(messages);
		}	
		return response;
	}

	
	public void approve(int id, List<String> messages) {

		String queryString = "UPDATE rental_list set status=? WHERE id=?;";
		try {
			PreparedStatement query = conn.prepareStatement(queryString);
			query.setInt(1, (byte) 1);
			query.setLong(2, id);

			int count = query.executeUpdate();
			if (count != 1) {
				messages.add("Error Occured!");
				messages.add("Please try again");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			messages.add("Error Occured!");
			messages.add("Please try again");
		}
	}

	public List<String> fetchRentalsToBeApproved(List<Object> rentals) {
		List<String> messages = new ArrayList<String>();
		String queryString = "SELECT * FROM rental_list WHERE status=? and enabled=?";
		try {
			PreparedStatement query = conn.prepareStatement(queryString);
			query.setInt(1, 0);
			query.setInt(2, (byte) 1);
            ResultSet rs = query.executeQuery();
            while(rs.next()) {
            	RentalList rental = new RentalList();
            	rental.setId(Integer.parseInt(rs.getString("id")));
            	rental.setTitle(rs.getString("title"));
            	rental.setRent(rs.getString("rent"));
            	rental.setCity(rs.getString("city"));
            	rental.setCountry(rs.getString("country"));
            	rental.setAvailability(rs.getString("availability"));
            	rental.setListingType(rs.getString("listing_type"));
            	rental.setLandlordId(Long.parseLong(rs.getString("landlord_id")));
            	rentals.add(rental);
            }
		} catch (SQLException e) {
			e.printStackTrace();
			messages.add("Error Occured!");
			messages.add("Please try again");
		}
		
		return messages;
	}

	public List<String> fetchByCity(List<Object> rentals, String city) {
		List<String> messages = new ArrayList<String>();
		String queryString = "SELECT * FROM rental_list WHERE city=? and status=? and enabled=?";
		try {
			PreparedStatement query = conn.prepareStatement(queryString);
			query.setString(1, city);
			query.setInt(2, (byte) 1);
			query.setInt(3, (byte) 1);
            ResultSet rs = query.executeQuery();
            while(rs.next()) {
            	RentalList rental = new RentalList();
            	rental.setId(Integer.parseInt(rs.getString("id")));
            	rental.setTitle(rs.getString("title"));
            	rental.setRent(rs.getString("rent"));
            	rental.setCity(rs.getString("city"));
            	rental.setCountry(rs.getString("country"));
            	rental.setAvailability(rs.getString("availability"));
            	rental.setListingType(rs.getString("listing_type"));
            	rental.setDescription(rs.getString("description"));
            	rental.setMaxOccupancy(rs.getString("max_occupancy"));
            	rental.setLandlordId(Long.parseLong(rs.getString("landlord_id")));
            	rental.setAddress(rs.getString("address"));
            	rentals.add(rental);
            }
		} catch (SQLException e) {
			e.printStackTrace();
			messages.add("Error Occured!");
			messages.add("Please try again");
		}
		
		return messages;	
	}

}
