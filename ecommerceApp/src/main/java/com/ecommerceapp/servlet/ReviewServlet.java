package com.ecommerceapp.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.ecommerceapp.model.Review;
import com.ecommerceapp.service.ReviewService;

import java.io.IOException;

@WebServlet("/ReviewServlet")
public class ReviewServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int orderItemId = Integer.parseInt(request.getParameter("orderItemId"));
        String reviewText = request.getParameter("reviewText");
        int starRating = Integer.parseInt(request.getParameter("starRating"));

        Review review = new Review(orderItemId, reviewText, starRating);
        ReviewService reviewService = new ReviewService();

        if (reviewService.addReview(review)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
