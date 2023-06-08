package com.ecommerceapp.model;

public class Reply {
    private int orderItemId;
    private String replyText;

    public Reply(int orderItemId, String replyText) {
        this.orderItemId = orderItemId;
        this.replyText = replyText;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public String getReplyText() {
        return replyText;
    }
}
