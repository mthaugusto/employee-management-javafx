package com.matheus.leite.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import com.matheus.leite.model.Employee;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmployeeDao {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3307/employee";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    public ObservableList<Employee> listEmployees() throws SQLException {

        ObservableList<Employee> listData = FXCollections.observableArrayList();
        String query = "SELECT * FROM employee_data";

        try (var connection = getConnection()) {
            var prepareStatement = connection.prepareStatement(query);
            var resultSet = prepareStatement.executeQuery();
            Employee employee;

            while (resultSet.next()) {
                employee = new Employee(
                        resultSet.getInt("employee_id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("gender"),
                        resultSet.getString("phoneNum"),
                        resultSet.getString("position"),
                        resultSet.getString("image"),
                        resultSet.getDate("date"));
                listData.add(employee);
            }
            return listData;
        }

    }

    public void createEmployee(String firstName, String lastName, String gender, String phoneNum,
            String position, String image, Date date) throws SQLException {

        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String query = "INSERT INTO employee_data (firstName, lastName, gender, phoneNum, position, image, date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (var connection = getConnection()) {
            var prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, firstName);
            prepareStatement.setString(2, lastName);
            prepareStatement.setString(3, gender);
            prepareStatement.setString(4, phoneNum);
            prepareStatement.setString(5, position);
            prepareStatement.setString(6, null);
            prepareStatement.setDate(7, sqlDate);

            prepareStatement.executeUpdate();

        }
    }

    public boolean employeeIdExists(String employeeIdString) throws SQLException {

        String query = "SELECT employee_id FROM employee_data WHERE employee_id = ?";
        try (var connection = getConnection()) {
            var prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, employeeIdString);
            var resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
            return false;
        }
    }
}
