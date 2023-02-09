package com.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.baseDesignPatterns.QueriesTemplate;
import com.baseDesignPatterns.ResponseState;
import com.model.ErrorResponse;
import com.model.FriendRequest;
import com.model.SuccessResponse;
import com.model.user.Student;

public class DashBoardQueries extends QueriesTemplate
{
    static Connection conn;
    static String query;
    String output;

    public DashBoardQueries()
    {
        conn = Database.getConnection();
    }

    @Override
    public void writeDb(Object obj, List<String> messages) {
        try {
            FriendRequest friendRequest = (FriendRequest) obj;

            query = "{Call prc_add_request(?,?)}";

            CallableStatement stmt;
            stmt = conn.prepareCall(query);
            stmt.setInt(1, friendRequest.getUserSenderId());
            stmt.setInt(2, friendRequest.getUserReceiverId());
            stmt.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            messages.add("Error Occured!");
            messages.add("Please try again");
        }
    }

    @Override
    public List<String> fetchByLandlordId(List<Object> objects, int landlordId) {
        return null;
    }

    @Override
    public ResponseState fetchById(Object obj, List<String> messages) {
        int studentRecieverId = (int) obj;
        ResponseState response;
        if(messages.get(0).equals("0")) {
        	query = "{Call prc_my_friend_request(?)}";
        }else {
        	query = "{Call prc_get_user_friends(?)}";
        }

        messages.clear();
        List<Student> requests = new ArrayList<Student>();
        try {
            CallableStatement stmt;
            stmt = conn.prepareCall(query);
            stmt.setInt(1, studentRecieverId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
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
                requests.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            messages.add("Error Occured!");
            messages.add("Please try again");
            return new ErrorResponse();
        }
        response = new SuccessResponse();
        response.setResponseObject(requests);
        return response;
    }

    public void update(Object obj, List<String> messages) {
        try{
            FriendRequest friendRequest = (FriendRequest) obj;

            query = "{Call prc_upd_request(?,?,?)}";

            CallableStatement stmt;
            stmt = conn.prepareCall(query);
            stmt.setInt(1, friendRequest.getUserSenderId());
            stmt.setInt(2, friendRequest.getUserReceiverId());
            stmt.setString(3, friendRequest.getAction());
            stmt.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            messages.add("Error Occured!");
            messages.add("Please try again");
        }
    }

    public List<String> fetchMyFriends(List<Object> users, int id) {
        return null;
    }
}
