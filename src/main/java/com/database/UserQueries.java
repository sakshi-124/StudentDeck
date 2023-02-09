package com.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.StudentDeck.Utils.PasswordUtil;
import com.baseDesignPatterns.QueriesTemplate;
import com.baseDesignPatterns.ResponseState;
import com.model.ErrorResponse;
import com.model.SuccessResponse;
import com.model.user.*;

public class UserQueries extends QueriesTemplate {
	static Connection conn;

	public UserQueries() {
		conn = Database.getConnection();
	}
	
	
	public ResponseState fetchById(Object obj, List<String> messages) {
		int id = (int) obj;
		Student student = new Student();
		ResponseState response;
		String queryString = "SELECT * FROM User WHERE idUser=?";
		try {
			PreparedStatement query = conn.prepareStatement(queryString);
			query.setInt(1, id);
            ResultSet rs = query.executeQuery();
            while(rs.next()) {
            	student.setFirstName(rs.getString("first_name"));
            	student.setLastName(rs.getString("last_name"));
            	student.setCountry(rs.getString("origin_country"));
            	student.setStudyCountry(rs.getString("course"));
            	student.setUniversity(rs.getString("university"));
            	student.setIntake(rs.getString("intake"));
            	student.setEmail(rs.getString("email"));
            	student.setCourse(rs.getString("course"));
            	student.setCity(rs.getString("city"));
				student.setId(Integer.valueOf(rs.getString("idUser")));
            	
            	response = new SuccessResponse();
            	response.setResponseObject(student);
            	return response;
            }
		} catch (SQLException e) {
			e.printStackTrace();
			messages.add("Error Occured!");
			messages.add("Please try again");
		}
		return new ErrorResponse();
	}

	public List<String> fetchByCity(List<Object> students, String cityAndID) {
		
		int id = Integer.parseInt(cityAndID.split(" ")[1]);
		String city = cityAndID.split(" ")[0];

		List<String> messages = new ArrayList<String>();
		String queryString = "SELECT * FROM User WHERE city=? and idUser!=?";
		try {
			PreparedStatement query = conn.prepareStatement(queryString);
			query.setString(1, city);
			query.setInt(2, id);
			ResultSet rs = query.executeQuery();
			while(rs.next())
			{
				Student student = new Student();
				student.setFirstName(rs.getString("first_name"));
				student.setLastName(rs.getString("last_name"));
				student.setCountry(rs.getString("origin_country"));
				student.setStudyCountry(rs.getString("course"));
				student.setUniversity(rs.getString("university"));
				student.setIntake(rs.getString("intake"));
				student.setEmail(rs.getString("email"));
				student.setCourse(rs.getString("course"));
				student.setCity(rs.getString("city"));
				student.setId(Integer.valueOf(rs.getString("idUser")));
				students.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			messages.add("Error Occured!");
			messages.add("Please try again");
		}

		return messages;
	}

	public ResponseState fetchStudent(int id) {
		IUserFactory userFactory = UserFactory.getInstance();
		Student student = (Student) userFactory.makeUser(UserType.STUDENT);
		ResponseState responseState;
		PasswordUtil passwordUtil = PasswordUtil.getInstance();
		conn = Database.getConnection();
		try {
			String query = "{Call fetchStudentById(?)}";
			CallableStatement stmt;
			stmt = conn.prepareCall(query);
			stmt.setInt(1, id);
			ResultSet resultSet  = stmt.executeQuery();
			while(resultSet.next())
			{
				student.setId(resultSet.getInt("idUser"));
				student.setEmail(resultSet.getString("email"));
				student.setFirstName(resultSet.getString("first_name"));
				student.setLastName(resultSet.getString("last_name"));
				student.setDateOfBirth(resultSet.getString("birthdate"));
				student.setCountryCode(Integer.toString(resultSet.getInt("country_code")));
				student.setPhone(resultSet.getString("phone"));
				student.setGender(resultSet.getString("gender"));
				student.setPassword(passwordUtil.decrypt(resultSet.getString("password")));
				student.setCountry(resultSet.getString("origin_country"));
				student.setStudyCountry(resultSet.getString("study_country"));
				student.setUniversity(resultSet.getString("university"));
				student.setCourse(resultSet.getString("course"));
				student.setIntake(resultSet.getString("intake"));
				student.setSecurityQuestion(resultSet.getString("security_question"));
				student.setSecurityAnswer(resultSet.getString("security_question_answer"));
				student.setStatus(resultSet.getInt("status"));
				student.setCity(resultSet.getString("city"));

				responseState = new SuccessResponse();
				responseState.setResponseObject(student);
				return responseState;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		responseState = new ErrorResponse();
		responseState.setMessages(List.of("There was an error fetching data"));
		responseState.setResponseObject(student);
		return responseState;
	}

	public ResponseState updateStudent(Object obj, List<String> messages) {
		Student student = (Student) obj;
		String updateQuery = "{Call updateUserById(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		ResponseState response;
		try {
			CallableStatement cStmt = conn.prepareCall(updateQuery);
			cStmt.setInt(1, student.getId());
			cStmt.setString(2, student.getEmail());
			cStmt.setString(3, student.getFirstName());
			cStmt.setString(4, student.getLastName());
			cStmt.setString(5, student.getDateOfBirth());
			cStmt.setInt(6, Integer.parseInt(student.getCountryCode()));
			cStmt.setString(7, student.getPhone());
			cStmt.setString(8, student.getGender());
			cStmt.setString(9, student.getCountry());
			cStmt.setString(10, student.getStudyCountry());
			cStmt.setString(11, student.getUniversity());
			cStmt.setString(12, student.getCourse());
			cStmt.setString(13, student.getIntake());
			cStmt.setString(14, student.getSecurityQuestion());
			cStmt.setString(15, student.getSecurityAnswer());
			cStmt.execute();

			response = new SuccessResponse();
			response.setResponseObject(student);
			return response;
		} catch (SQLException e) {
			e.printStackTrace();
			messages.add("Error Occured!");
			messages.add("Please try again");
		}
		return new ErrorResponse();
	}

	public ResponseState fetchLandlord(int id) {
		IUserFactory userFactory = UserFactory.getInstance();
		LandlordUser landlordUser = (LandlordUser) userFactory.makeUser(UserType.LANDLORD);
		conn = Database.getConnection();
		ResponseState responseState;
		try {
			String query = "{Call fetchLandlordById(?)}";
			CallableStatement stmt;
			stmt = conn.prepareCall(query);
			stmt.setInt(1, id);
			ResultSet resultSet  = stmt.executeQuery();
			PasswordUtil passwordUtil = PasswordUtil.getInstance();
			while(resultSet.next())
			{
				landlordUser.setId(resultSet.getInt("idLandlord"));
				landlordUser.setEmail(resultSet.getString("email"));
				landlordUser.setFirstName(resultSet.getString("first_name"));
				landlordUser.setLastName(resultSet.getString("last_name"));
				landlordUser.setDateOfBirth(resultSet.getString("birthdate"));
				landlordUser.setCountryCode(Integer.toString(resultSet.getInt("country_code")));
				landlordUser.setPhone(resultSet.getString("phone"));
				landlordUser.setGender(resultSet.getString("gender"));
				landlordUser.setPassword(passwordUtil.decrypt(resultSet.getString("password")));
				landlordUser.setCountry(resultSet.getString("country"));
				landlordUser.setSecurityQuestion(resultSet.getString("security_question"));
				landlordUser.setSecurityAnswer(resultSet.getString("security_question_answer"));
				landlordUser.setStatus(resultSet.getInt("status"));

				responseState = new SuccessResponse();
				responseState.setResponseObject(landlordUser);
				return responseState;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ErrorResponse();
	}

	public ResponseState updateLandLord(Object obj, List<String> messages) {
		LandlordUser landlordUser = (LandlordUser) obj;
		String updateQuery = "{Call updateLandlord(?,?,?,?,?,?,?,?,?,?,?)}";
		ResponseState response;
		try {
			CallableStatement cStmt = conn.prepareCall(updateQuery);
			cStmt.setInt(1, landlordUser.getId());
			cStmt.setString(2, landlordUser.getEmail());
			cStmt.setString(3, landlordUser.getFirstName());
			cStmt.setString(4, landlordUser.getLastName());
			cStmt.setString(5, landlordUser.getDateOfBirth());
			cStmt.setInt(6, Integer.parseInt(landlordUser.getCountryCode()));
			cStmt.setString(7, landlordUser.getPhone());
			cStmt.setString(8, landlordUser.getGender());
			cStmt.setString(9, landlordUser.getCountry());
			cStmt.setString(10, landlordUser.getSecurityQuestion());
			cStmt.setString(11, landlordUser.getSecurityAnswer());
			cStmt.execute();

			response = new SuccessResponse();
			response.setResponseObject(landlordUser);
			return response;
		} catch (SQLException e) {
			e.printStackTrace();
			messages.add("Error Occured!");
			messages.add("Please try again");
		}
		return new ErrorResponse();
	}

	public ResponseState fetchAdmin(int id) {
		IUserFactory userFactory = UserFactory.getInstance();
		AdminUser adminUser = (AdminUser) userFactory.makeUser(UserType.ADMIN);
		conn = Database.getConnection();
		ResponseState responseState;
		try {
			String query = "{Call fetchByAdminId(?)}";
			CallableStatement stmt;
			stmt = conn.prepareCall(query);
			stmt.setInt(1, id);
			ResultSet resultSet  = stmt.executeQuery();
			while(resultSet.next())
			{
				adminUser.setId(resultSet.getInt("adminId"));
				adminUser.setEmail(resultSet.getString("email"));
				adminUser.setFirstName(resultSet.getString("first_name"));
				adminUser.setLastName(resultSet.getString("last_name"));
				adminUser.setPassword(resultSet.getString("password"));
				adminUser.setSecurityQuestion(resultSet.getString("security_question"));
				adminUser.setSecurityAnswer(resultSet.getString("security_question_answer"));
				adminUser.setStatus(resultSet.getInt("status"));

				responseState = new SuccessResponse();
				responseState.setResponseObject(adminUser);
				return responseState;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ErrorResponse();
	}

	public ResponseState updateAdmin(Object obj, List<String> messages) {
		AdminUser adminUser = (AdminUser) obj;
		String updateQuery = "{Call updateAdmin(?,?,?)}";
		ResponseState response;
		try {
			CallableStatement cStmt = conn.prepareCall(updateQuery);
			cStmt.setString(2, adminUser.getEmail());
			cStmt.setString(3, adminUser.getFirstName());
			cStmt.setString(4, adminUser.getLastName());
			cStmt.execute();

			response = new SuccessResponse();
			response.setResponseObject(adminUser);
			return response;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ErrorResponse();
	}

	public Integer userEmailCheck(String email, int userType) {
		Connection conn;
		Integer output = 0;
		try {
			conn = Database.getConnection();
			String query = "{Call userCheck(?,?)}";

			CallableStatement cStmt;
			cStmt = conn.prepareCall(query);
			cStmt.setString(1,email);
			cStmt.setInt(2, userType);
			cStmt.registerOutParameter(3,java.sql.Types.INTEGER);
			cStmt.execute();
			output = cStmt.getInt(4);

			return output;
		}
		catch (Exception ex)
		{
			return output;
		}
	}
}
