package com.ecommerceapp.servlet;

import com.ecommerceapp.service.OrderService;
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
    private OrderService orderService = new OrderService();
    private Gson gson = new Gson();
    private Type type = new TypeToken<HashMap<Integer, Integer>>(){}.getType();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        int customerId = (int) session.getAttribute("userId");
        Map<Integer, Integer> items = gson.fromJson(request.getReader(), type);

        try {
            // Create a new order
            Date utilDate = new Date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            int orderId = orderService.createOrder(customerId, "Pending", sqlDate);

            // Insert all the items in the cart into the OrderItems table
            orderService.addItemsToOrder(orderId, items);

            // Clear the cart
            items.clear();
            session.setAttribute("cart", items);

            response.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException ex) {
            Logger.getLogger(OrderServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}