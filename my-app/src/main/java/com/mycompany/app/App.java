package com.mycompany.app;

import java.sql.*;

public class App {

    public static void main( String[] args ) throws SQLException {
        //create connection for a server installed in localhost, with a user "root" with no password
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/workshop_app?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Amsterdam"
                , "springuser", "A;pKcpQ/Y*Gq42apnJb8agP.p;=&A)gsfq")) {
            // create a Statement
            try (Statement stmt = conn.createStatement()) {
                //execute query
                try (ResultSet rs = stmt.executeQuery("SELECT username FROM user")) {
                    //position result to first
                    rs.first();
                    System.out.println(rs.getString("username")); //result is "Hello World!"
                }
            }
        }
    }
}