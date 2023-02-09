package com.database.authentication;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;

import com.database.Database;
import com.model.user.AdminUser;

public class AdminAuthStrategy implements UserAuthStrategy {
    Connection conn;

    public AdminAuthStrategy() {
        conn = Database.getConnection();
    }

    @Override
    public String signIn(Object user) {
        try {
            AdminUser adminUser = (AdminUser) user;
            String query;
            String output;
            query = "{Call prc_user_login(?,?,?,?)}";

            CallableStatement stmt;
            stmt = conn.prepareCall(query);
            stmt.setString(1, adminUser.getEmail());
            stmt.setString(2, adminUser.getPassword());
            stmt.setString(3, "Admin");

            stmt.registerOutParameter(4, Types.VARCHAR);

            stmt.execute();
            output = stmt.getString(4);

            return output;
        } catch (Exception ex) {
            return "0-NA";
            //closeConnections(con);
        }

    }

    @Override
    public void signUp(Object user) {
        AdminUser adminUser = (AdminUser) user;
        String signUpQuery = "{Call registerAdminUser(?,?,?,?,?,?,?)}";
        try {
            PreparedStatement cStmt = conn.prepareCall(signUpQuery);
            cStmt.setString(1, adminUser.getEmail());
            cStmt.setString(2, adminUser.getFirstName());
            cStmt.setString(3, adminUser.getLastName());
            cStmt.setString(4, adminUser.getPassword());
            cStmt.setString(5, adminUser.getSecurityQuestion());
            cStmt.setString(6, adminUser.getSecurityAnswer());
            cStmt.setInt(7, adminUser.getStatus());
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

    }

    @Override
    public void updatePassword(int id, String updatedPassword) {

    }
}
