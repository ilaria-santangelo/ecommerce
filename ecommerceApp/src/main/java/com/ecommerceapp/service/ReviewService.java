package com.ecommerceapp.service;

import com.ecommerceapp.model.Review;
import com.ecommerceapp.utility.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ReviewService {
    public boolean addReview(Review review) {
        boolean isAdded = false;
        try {
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO Reviews (order_item_id, review_text, star_rating) VALUES (" +
                    review.getOrderItemId() + ", '" + review.getReviewText() + "', " + review.getStarRating() + ")";
            int result = statement.executeUpdate(sql);
            isAdded = result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAdded;
    }
}
