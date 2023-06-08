package com.ecommerceapp.service;

import com.ecommerceapp.model.Reply;
import com.ecommerceapp.utility.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ReplyService {
    public boolean addReply(Reply reply) {
        boolean isAdded = false;
        try {
            Connection connection = DatabaseManager.getConnection();
            Statement statement = connection.createStatement();
            String sql = "UPDATE Reviews SET reply = '"
                    + reply.getReplyText() + "' WHERE order_item_id = " + reply.getOrderItemId();
            int result = statement.executeUpdate(sql);
            isAdded = result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAdded;
    }
}
