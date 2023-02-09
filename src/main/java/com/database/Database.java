package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	
    private static Connection con = null;
    
    static
    {
        String url = "jdbc:mysql://db-5308.cs.dal.ca:3306/CSCI5308_2_DEVINT";
        String user = "CSCI5308_2_DEVINT_USER";
        String pass = "NdC8Wmhx6Q";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection()
    {
        return con;
    }

}
