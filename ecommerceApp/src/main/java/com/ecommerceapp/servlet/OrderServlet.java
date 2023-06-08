package com.ecommerceapp.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;

import com.ecommerceapp.utility.DatabaseManager;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        int customerID = (int) session.getAttribute("userId");
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<Integer, Integer>>(){}.getType();
        Map<Integer, Integer> cart = gson.fromJson(request.getReader(), type);
        String orderStatus = "Pending";
        Date orderDate = new Date(); // Current date

        Connection connection = DatabaseManager.getConnection();
        try {
            // Begin transaction
            connection.setAutoCommit(false);

            // Create a new order
            String orderQuery = "INSERT INTO Orders (customer_id, status, order_date) VALUES ('" + customerID + "', '" + orderStatus + "', '" + new java.sql.Date(orderDate.getTime()) + "')";
            Statement orderStatement = connection.createStatement();
            orderStatement.executeUpdate(orderQuery, Statement.RETURN_GENERATED_KEYS);

            // Get the ID of the new order
            ResultSet generatedKeys = orderStatement.getGeneratedKeys();
            generatedKeys.next();
            int orderID = generatedKeys.getInt(1);

            // Insert all the items in the cart into the OrderItems table
            for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
                int productID = entry.getKey();
                int quantity = entry.getValue();

                String itemQuery = "INSERT INTO OrderItems (order_id, product_id, quantity) VALUES ('" + orderID + "', '" + productID + "', '" + quantity + "')";
                Statement itemStatement = connection.createStatement();
                itemStatement.executeUpdate(itemQuery);
            }

            // Commit transaction
            connection.commit();

            // Clear the cart
            cart.clear();
            session.setAttribute("cart", cart);

            response.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException ex) {
            // If any error occurs, rollback changes
            try {
                connection.rollback();
            } catch (SQLException rollEx) {
                Logger.getLogger(OrderServlet.class.getName()).log(Level.SEVERE, null, rollEx);
            }

            Logger.getLogger(OrderServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                Logger.getLogger(OrderServlet.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}