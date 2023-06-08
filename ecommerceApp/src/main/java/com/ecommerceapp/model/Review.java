package com.ecommerceapp.model;

public class Review {
    private int orderItemId;
    private String reviewText;
    private int starRating;

    public Review(int orderItemId, String reviewText, int starRating) {
        this.orderItemId = orderItemId;
        this.reviewText = reviewText;
        this.starRating = starRating;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public int getStarRating() {
        return starRating;
    }
}
