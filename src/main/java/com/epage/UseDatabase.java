package com.epage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class UseDatabase {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/epage_user?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "qwe123";

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("[e-Page UseDatabase] getConnection ");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static boolean authenticateUser(String userId, String password) {
        String sql = "SELECT 1 FROM users WHERE userId = ? AND password = ?";

        System.out.println("[e-Page UseDatabase] authenticateUser userId = " + userId + ", password = " + password);

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // If a record is found, the user is authenticated
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // 사용자 저장 메서드
    public static boolean saveUser(String role, String username, String userId, String password, Date expireDate) {
        String sql = "INSERT INTO users (role, username, userId, password, expireDate) VALUES (?, ?, ?, ?, ?)";

        System.out.println("[e-Page UseDatabase] authenticateUser role = " + role + ", username = " + username+ ", userId = " + userId+ ", password = " + password+ ", expireDate = " + expireDate);

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, role);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, userId);
            preparedStatement.setString(4, password);
            preparedStatement.setDate(5, (java.sql.Date) expireDate);

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {

            System.err.println("Error during saving user: " + e.getMessage());

            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkDuplication(String userId) {
        String sql = "SELECT COUNT(*) FROM users WHERE userId = ?";

        System.out.println("[e-Page UseDatabase] checkDuplication userId = " + userId);

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; //  중복
                }
            }
        } catch (Exception e) {
            System.err.println("Error during check user id: " + e.getMessage());
            e.printStackTrace();
        }
        return false; // 예외가 발생하거나 COUNT(*)가 0인 경우 false 반환
    }

}
