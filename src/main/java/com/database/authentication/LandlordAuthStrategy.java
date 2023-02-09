package com.database.authentication;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;

import com.database.Database;
import com.model.user.LandlordUser;

public class LandlordAuthStrategy implements UserAuthStrategy {
    Connection conn;

    public LandlordAuthStrategy() {
        conn = Database.getConnection();
    }
    @Override
    public String  signIn(Object user) {
        try {
            LandlordUser landlordUser = (LandlordUser) user;
            String query;
            String output;
            query = "{Call prc_user_login(?,?,?,?)}";

            CallableStatement stmt;
            stmt = conn.prepareCall(query);
            stmt.setString(1,landlordUser.getEmail());
            stmt.setString(2,landlordUser.getPassword());
            stmt.setString(3,"Landlord");

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
        LandlordUser landlordUser = (LandlordUser) user;
        String signUpQuery = "{Call registerLandlordUser(?,?,?,?,?,?,?,?,?,?,?,?)}";
        try {
            PreparedStatement cStmt = conn.prepareCall(signUpQuery);
            cStmt.setString(1, landlordUser.getEmail());
            cStmt.setString(2, landlordUser.getFirstName());
            cStmt.setString(3, landlordUser.getLastName());
            cStmt.setString(4, landlordUser.getDateOfBirth());
            cStmt.setInt(5, Integer.parseInt(landlordUser.getCountryCode()));
            cStmt.setString(6, landlordUser.getPhone());
            cStmt.setString(7, landlordUser.getGender());
            cStmt.setString(8, landlordUser.getPassword());
            cStmt.setString(9, landlordUser.getCountry());
            cStmt.setString(10, landlordUser.getSecurityQuestion());
            cStmt.setString(11, landlordUser.getSecurityAnswer());
            cStmt.setInt(12, landlordUser.getStatus());
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
        String query = "{Call deactivateLandlordUser(?)}";
        try {
            PreparedStatement cStmt = conn.prepareCall(query);
            cStmt.setInt(1, userId);
            cStmt.execute();
        } catch (Exception e) {
            //TODO: Throw message
            e.printStackTrace();
        }
    }

    @Override
    public void updatePassword(int id, String updatedPassword) {
        String query = "{Call resetLandlordPassword(?,?)}";
        try {
            PreparedStatement cStmt = conn.prepareCall(query);
            cStmt.setInt(1, id);
            cStmt.setString(2, updatedPassword);
            cStmt.execute();
        } catch (Exception e) {
            //TODO: Throw message
            e.printStackTrace();
        }
    }
}

