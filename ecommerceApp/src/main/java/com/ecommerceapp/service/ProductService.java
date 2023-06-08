package com.ecommerceapp.service;

import com.ecommerceapp.utility.DatabaseManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.sql.*;

public class ProductService {

    public void addProduct(int vendorId, String name, String description, double price, String image_path) {
        try {
            Connection connection = DatabaseManager.getConnection();
            String sqlQuery = "INSERT INTO Products (vendor_id, product_name, product_description, product_price, product_image_path) VALUES (?, ?, ?, ?, ?)";
    
            // Create a PreparedStatement with the option to retrieve generated keys
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, vendorId);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, description);
            preparedStatement.setDouble(4, price);
            preparedStatement.setString(5, image_path);
    
            preparedStatement.executeUpdate();
    
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                // Use the generated ID as needed
                System.out.println("Generated ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public String getProductsByUserId(int userId) {
        String json = null;
        try {
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM Products WHERE vendor_id = " + userId;
            ResultSet resultSet = statement.executeQuery(sql);

            json = resultSetToJson(resultSet);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    private String resultSetToJson(ResultSet resultSet) throws SQLException {
        JsonArray jsonArray = new JsonArray();
        ResultSetMetaData metadata = resultSet.getMetaData();
        int columnCount = metadata.getColumnCount();

        while (resultSet.next()) {
            JsonObject jsonObject = new JsonObject();

            for (int i = 1; i <= columnCount; i++) {
                String columnName = metadata.getColumnName(i);
                jsonObject.addProperty(columnName, resultSet.getString(i));
            }

            jsonArray.add(jsonObject);
        }

        return jsonArray.toString();
    }

    public String getProductsForCustomer(int userId) {
        String json = null;
        try {
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT Products.*, Vendors.user_id FROM Products JOIN Vendors ON Products.vendor_id = Vendors.user_id";
            ResultSet resultSet = statement.executeQuery(sql);

            json = resultSetToJson(resultSet, userId);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    private String resultSetToJson(ResultSet resultSet, int userId) throws SQLException {
        JsonArray jsonArray = new JsonArray();
        ResultSetMetaData metadata = resultSet.getMetaData();
        int columnCount = metadata.getColumnCount();

        while (resultSet.next()) {
            JsonObject jsonObject = new JsonObject();

            for (int i = 1; i <= columnCount; i++) {
                String columnName = metadata.getColumnName(i);
                jsonObject.addProperty(columnName, resultSet.getString(i));
            }

            jsonObject.addProperty("userId", userId); // Add userId to the JSON object

            jsonArray.add(jsonObject);
        }

        return jsonArray.toString();
    }

    public String getSingleProduct(String productId) {
        String json = null;
        try {
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM Products WHERE ID = " + productId; // Be aware of SQL Injection.
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                json = resultSingleSetToJson(resultSet);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    private String resultSingleSetToJson(ResultSet resultSet) throws SQLException {
        JsonObject jsonObject = new JsonObject();
        ResultSetMetaData metadata = resultSet.getMetaData();
        int columnCount = metadata.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = metadata.getColumnName(i);
            jsonObject.addProperty(columnName, resultSet.getString(i));
        }

        return jsonObject.toString();
    }
}
