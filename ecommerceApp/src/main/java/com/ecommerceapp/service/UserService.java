package com.ecommerceapp.service;

import com.ecommerceapp.model.User;
import com.ecommerceapp.utility.DatabaseManager;

import java.sql.*;

public class UserService {

    public User login(String email, String password) {
        User user = null;
        try {
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users WHERE email = '" + email + "' AND Password = '" + password + "'");

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("ID"));
                user.setUsername(resultSet.getString("Username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("Password"));
                user.setUserType(resultSet.getString("UserType"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User signup(String username, String email, String password, String userType) {
        User user = null;
        try {
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate("INSERT INTO Users(Username, email, Password, UserType) VALUES ('" + username + "', '" + email + "', '" + password + "', '" + userType + "')", Statement.RETURN_GENERATED_KEYS);

            if (result > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user = new User();
                    user.setId(generatedKeys.getInt(1));
                    user.setUsername(username);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setUserType(userType);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    

    
}
