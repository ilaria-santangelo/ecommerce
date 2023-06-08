package com.ecommerceapp.service;

import com.ecommerceapp.utility.DatabaseManager;

import java.sql.*;
import java.util.Map;

public class OrderService {

    public int createOrder(int customerId, String status, Date date) throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        Statement statement = connection.createStatement();

        String orderQuery = "INSERT INTO Orders (customer_id, status, order_date) VALUES ('" + customerId + "', '" + status + "', '" + new java.sql.Date(date.getTime()) + "')";

        statement.executeUpdate(orderQuery, Statement.RETURN_GENERATED_KEYS);
        ResultSet generatedKeys = statement.getGeneratedKeys();
        generatedKeys.next();

        return generatedKeys.getInt(1);  // return the new Order ID
    }

    public void addItemsToOrder(int orderId, Map<Integer, Integer> items) throws SQLException {
        Connection connection = DatabaseManager.getConnection();

        for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();

            String itemQuery = "INSERT INTO OrderItems (order_id, product_id, quantity) VALUES ('" + orderId + "', '" + productId + "', '" + quantity + "')";
            Statement itemStatement = connection.createStatement();

            itemStatement.executeUpdate(itemQuery);
        }
    }
}
