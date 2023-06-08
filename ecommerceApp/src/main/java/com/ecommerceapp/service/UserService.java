package com.ecommerceapp.service;

import com.ecommerceapp.model.User;
import com.ecommerceapp.utility.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    private Connection connection;

    public UserService() {
        this.connection = DatabaseManager.getConnection();
    }

    public User createUser(String username, String email, String password, String userType, String bio) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Users(Username, email, Password, UserType, Bio) VALUES (?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, email);
        preparedStatement.setString(3, password);
        preparedStatement.setString(4, userType);
        preparedStatement.setString(5, bio);
        preparedStatement.executeUpdate();

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            int generatedId = generatedKeys.getInt(1);
            return new User(username, email, password, userType, bio);
        } else {
            return null;
        }
    }

    public User getUser(String email, String password) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE email = ? AND Password = ?");
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String username = resultSet.getString("Username");
            String userType = resultSet.getString("UserType");
            String bio = resultSet.getString("Bio");
            return new User(username, email, password, userType, bio);
        } else {
            return null;
        }
    }

    // Additional methods can be added here as per your requirements...
}
