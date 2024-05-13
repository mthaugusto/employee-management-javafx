package com.matheus.leite.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.matheus.leite.model.User;

public class LoginDao {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3307/employee";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    public User authenticate(String username, String password) throws SQLException {
        var query = "SELECT * FROM tb_employee_users WHERE username = ? AND password = ?";
        try (var connection = getConnection()) {
            var prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, username);
            prepareStatement.setString(2, password);
            var resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                return 
                new User(
                    resultSet.getInt("id"), 
                    resultSet.getString("username"),
                    resultSet.getString("password")
                );
            }
            return null;
        }
    }

}
