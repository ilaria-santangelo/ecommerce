package com.ecommerceapp.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.ecommerceapp.model.Reply;
import com.ecommerceapp.service.ReplyService;

import java.io.IOException;

@WebServlet("/ReplyServlet")
public class ReplyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int orderItemId = Integer.parseInt(request.getParameter("orderItemId"));
        String replyText = request.getParameter("replyText");

        Reply reply = new Reply(orderItemId, replyText);
        ReplyService replyService = new ReplyService();

        if (replyService.addReply(reply)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
