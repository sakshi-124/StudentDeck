package com.database.authentication;

import com.database.Database;
import com.model.user.Student;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;

public class StudentAuthStrategy implements UserAuthStrategy {
    Connection conn;

    public StudentAuthStrategy() {
        conn = Database.getConnection();
    }

    @Override
    public String  signIn(Object user) {
        try {
            Student student = (Student) user;
            String query;
            String output;
            query = "{Call prc_user_login(?,?,?,?)}";

            CallableStatement stmt;
            stmt = conn.prepareCall(query);
            stmt.setString(1,student.getEmail());
            stmt.setString(2,student.getPassword());
            stmt.setString(3,"Student");

            stmt.registerOutParameter(4, Types.VARCHAR);

            stmt.execute();
            output = stmt.getString(4);

            return output;
        }
        catch (Exception ex)
        {
            return "0-NA";
            //closeConnections(con);
        }

    }

    @Override
    public void signUp(Object user) {
        Student student = (Student) user;
        String signUpQuery = "{Call registerStudentUser(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        try {
            PreparedStatement cStmt = conn.prepareCall(signUpQuery);
            cStmt.setString(1, student.getEmail());
            cStmt.setString(2, student.getFirstName());
            cStmt.setString(3, student.getLastName());
            cStmt.setString(4, student.getDateOfBirth());
            cStmt.setInt(5, Integer.parseInt(student.getCountryCode()));
            cStmt.setString(6, student.getPhone());
            cStmt.setString(7, student.getGender());
            cStmt.setString(8, student.getPassword());
            cStmt.setString(9, student.getCountry());
            cStmt.setString(10, student.getStudyCountry());
            cStmt.setString(11, student.getUniversity());
            cStmt.setString(12, student.getCourse());
            cStmt.setString(13, student.getIntake());
            cStmt.setString(14, student.getSecurityQuestion());
            cStmt.setString(15, student.getSecurityAnswer());
            cStmt.setInt(16, student.getStatus());
            cStmt.setString(17, student.getCity());
            cStmt.execute();
        } catch (Exception e) {
            //TODO: Throw message
            e.printStackTrace();
        } finally {
//            db.closeConnections(conn);
        }
    }

    @Override
    public void deactivate(Integer userId) {
        String deactivateUserQuery = "Call deactivateStudentUser(?);";
        try {
            CallableStatement cStmnt = conn.prepareCall(deactivateUserQuery);
            cStmnt.setInt(1, userId);
            cStmnt.execute();

        } catch (Exception e) {
            //TODO: Throw error to user
            e.printStackTrace();
        } finally {
//            db.closeConnections(conn);
        }
    }
    @Override
    public void updatePassword(int id, String updatedPassword) {
        String updatePasswordQuery = "{Call updateStudentPassword(?,?)}";
        try {
            CallableStatement cStmnt = conn.prepareCall(updatePasswordQuery);
            cStmnt.setInt(1, id);
            cStmnt.setString(2, updatedPassword);
            cStmnt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
