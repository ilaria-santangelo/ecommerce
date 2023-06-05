package com.ecommerceapp.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

import com.ecommerceapp.utility.DatabaseManager;

@WebServlet("/ReviewServlet")
public class ReviewServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderItemId = request.getParameter("orderItemId");
        String reviewText = request.getParameter("reviewText");
        String starRating = request.getParameter("starRating");

        try {
            // Get the database connection
            Connection conn = DatabaseManager.getConnection();

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO Reviews (order_item_id, review_text, star_rating) VALUES ('"
                    + orderItemId + "', '" + reviewText + "', " + starRating + ")";

            // Update the review in the database
            int result = stmt.executeUpdate(sql);

            if (result > 0) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

            stmt.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
