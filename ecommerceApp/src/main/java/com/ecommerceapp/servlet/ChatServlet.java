package com.ecommerceapp.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ecommerceapp.utility.Message;

@WebServlet(name = "chatServlet", urlPatterns = {"/chat"})
public class ChatServlet extends HttpServlet {

    private static final ConcurrentLinkedQueue<Message> messages = new ConcurrentLinkedQueue<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String messageContent = req.getParameter("message");
        
        // Get the sender from the request, e.g., user or vendor
        String sender = req.getParameter("sender");

        // Create a new Message object with content and sender
        Message message = new Message(messageContent, sender);
        
        messages.add(message);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.sendRedirect(req.getContextPath() + "/src/main/webapp/views/chat.html");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        // Create a JSON array to store the messages
        JSONArray jsonMessages = new JSONArray();
        
        for (Message message : messages) {
            // Create a JSON object for each message
            JSONObject jsonMessage = new JSONObject();
            jsonMessage.put("content", message.getContent());
            jsonMessage.put("sender", message.getSender());
            
            jsonMessages.put(jsonMessage);
        }
        
        // Write the JSON array as the response
        resp.getWriter().write(jsonMessages.toString());
    }
}
