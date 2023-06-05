package com.ecommerceapp.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ecommerceapp.utility.DatabaseManager;

@WebServlet("/ReviewServlet")
public class ReviewServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int customerId = (int) session.getAttribute("userId");

        int orderItemId = Integer.parseInt(request.getParameter("orderItemId"));
        int starRating = Integer.parseInt(request.getParameter("starRating"));
        String reviewText = request.getParameter("reviewText");

        Connection connection = DatabaseManager.getConnection();
        String sql = "INSERT INTO Reviews (order_item_id, star_rating, review_text) VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, orderItemId);
            preparedStatement.setInt(2, starRating);
            preparedStatement.setString(3, reviewText);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

