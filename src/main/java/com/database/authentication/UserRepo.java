package com.database.authentication;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.baseDesignPatterns.ResponseState;
import com.database.Database;
import com.model.ErrorResponse;
import com.model.SuccessResponse;
import com.model.user.AdminUser;
import com.model.user.IUserFactory;
import com.model.user.LandlordUser;
import com.model.user.Student;
import com.model.user.User;
import com.model.user.UserFactory;
import com.model.user.UserType;

public class UserRepo {
    Connection conn;
    public Integer userCheck(String email, int userType) {
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
            //closeConnections(con);
        }
    }

    public User fetchStudentById(int id) {
        IUserFactory userFactory = UserFactory.getInstance();
        Student student = (Student) userFactory.makeUser(UserType.STUDENT);
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
                student.setPassword(resultSet.getString("password"));
                student.setCountry(resultSet.getString("origin_country"));
                student.setStudyCountry(resultSet.getString("study_country"));
                student.setUniversity(resultSet.getString("university"));
                student.setCourse(resultSet.getString("course"));
                student.setIntake(resultSet.getString("intake"));
                student.setSecurityQuestion(resultSet.getString("security_question"));
                student.setSecurityAnswer(resultSet.getString("security_question_answer"));
                student.setStatus(resultSet.getInt("status"));

            }
        } catch (SQLException e) {
           e.printStackTrace();
        }
       return student;
    }

    public ResponseState updateUser(Object user){
        Student student = (Student) user;
        String updateQuery = "{Call updateUserById(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        ResponseState responseState = null;
        try {
            conn = Database.getConnection();
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
            responseState = new SuccessResponse();
            responseState.setMessages(List.of("Successfully updated user"));
            responseState.setResponseObject(student);

        } catch (Exception e) {
            System.out.println("Exception");
            responseState = new ErrorResponse();
            responseState.setMessages(List.of("Something went wrong updating user"));
            responseState.setResponseObject(student);
            e.printStackTrace();
        } finally {
//            db.closeConnections(conn);
        }
        return responseState;
    }

    public User fetchLandlordById(int id) {
        IUserFactory userFactory = UserFactory.getInstance();
        LandlordUser landlordUser = (LandlordUser) userFactory.makeUser(UserType.LANDLORD);
        conn = Database.getConnection();
        try {
            String query = "{Call fetchLandlordById(?)}";
            CallableStatement stmt;
            stmt = conn.prepareCall(query);
            stmt.setInt(1, id);
            ResultSet resultSet  = stmt.executeQuery();
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
                landlordUser.setPassword(resultSet.getString("password"));
                landlordUser.setCountry(resultSet.getString("country"));
                landlordUser.setSecurityQuestion(resultSet.getString("security_question"));
                landlordUser.setSecurityAnswer(resultSet.getString("security_question_answer"));
                landlordUser.setStatus(resultSet.getInt("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return landlordUser;
    }

    public ResponseState updateLandlord(Object user){
        LandlordUser landlord = (LandlordUser) user;
        String updateQuery = "{Call updateLandlord(?,?,?,?,?,?,?,?,?,?,?)}";
        ResponseState responseState = null;
        try {
            conn = Database.getConnection();
            CallableStatement cStmt = conn.prepareCall(updateQuery);
            cStmt.setInt(1, landlord.getId());
            cStmt.setString(2, landlord.getEmail());
            cStmt.setString(3, landlord.getFirstName());
            cStmt.setString(4, landlord.getLastName());
            cStmt.setString(5, landlord.getDateOfBirth());
            cStmt.setInt(6, Integer.parseInt(landlord.getCountryCode()));
            cStmt.setString(7, landlord.getPhone());
            cStmt.setString(8, landlord.getGender());
            cStmt.setString(9, landlord.getCountry());
            cStmt.setString(10, landlord.getSecurityQuestion());
            cStmt.setString(11, landlord.getSecurityAnswer());
            cStmt.execute();
            responseState = new SuccessResponse();
            responseState.setMessages(List.of("Successfully updated user"));
            responseState.setResponseObject(landlord);

        } catch (Exception e) {
            System.out.println("Exception");
            responseState = new ErrorResponse();
            responseState.setMessages(List.of("Something went wrong updating user"));
            responseState.setResponseObject(landlord);
            e.printStackTrace();
        } finally {
//            db.closeConnections(conn);
        }
        return responseState;
    }

    public User fetchAdminById(int id) {
        IUserFactory userFactory = UserFactory.getInstance();
        AdminUser adminUser = (AdminUser) userFactory.makeUser(UserType.ADMIN);
        conn = Database.getConnection();
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminUser;
    }

}
