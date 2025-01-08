package com.epage.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectionTest {

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/epage_user?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "qwe123";

    public static void main(String[] args) {
        // Test MySQL connection
        try {
            System.out.println("Loading MySQL driver...");
            Class.forName("com.mysql.cj.jdbc.Driver"); // Explicitly load MySQL driver
            System.out.println("Driver loaded successfully!");

            System.out.println("Connecting to the database...");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connection successful!");

            // Close connection
            connection.close();
            System.out.println("Connection closed.");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database!");
            e.printStackTrace();
        }
    }
}
