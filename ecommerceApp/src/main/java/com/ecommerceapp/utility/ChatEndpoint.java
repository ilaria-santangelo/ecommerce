package com.ecommerceapp.utility;


import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat/{customerID}/{vendorID}")
public class ChatEndpoint {
    private static Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("customerID") String customerID, @PathParam("vendorID") String vendorID) throws IOException {
        sessions.put(session.getId(), session);
        broadcastMessage(customerID, vendorID, "User " + session.getId() + " joined the chat");
    }

    @OnMessage
    public void onMessage(Session session, String message, @PathParam("customerID") String customerID, @PathParam("vendorID") String vendorID) throws IOException {
        broadcastMessage(customerID, vendorID, "User " + session.getId() + ": " + message);
    }

    @OnClose
    public void onClose(Session session, @PathParam("customerID") String customerID, @PathParam("vendorID") String vendorID) throws IOException {
        sessions.remove(session.getId());
        broadcastMessage(customerID, vendorID, "User " + session.getId() + " left the chat");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Error handling
    }

    private void broadcastMessage(String customerID, String vendorID, String message) {
        sessions.values().stream()
                .filter(session -> session.isOpen() && customerID.equals(session.getPathParameters().get("customerID")) && vendorID.equals(session.getPathParameters().get("vendorID")))
                .forEach(session -> {
                    try {
                        session.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
}
