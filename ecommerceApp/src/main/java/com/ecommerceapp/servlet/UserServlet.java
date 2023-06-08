package com.ecommerceapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;

import com.ecommerceapp.model.User;
import com.ecommerceapp.service.UserService;
import com.ecommerceapp.utility.DatabaseManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");

        if ("login".equalsIgnoreCase(action)) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            User user = userService.login(email, password);

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getId());
                if ("vendor".equalsIgnoreCase(user.getUserType())) {
                    response.sendRedirect(request.getContextPath() + "src/main/webapp/views/profile.html");
                } else if ("customer".equalsIgnoreCase(user.getUserType())) {
                    response.sendRedirect(request.getContextPath() + "src/main/webapp/views/customer.html");
                }
            } else {
                out.write("<html><body>failure: " + request.getParameter("failure") + "</body></html>");
            }
        } else if ("signup".equalsIgnoreCase(action)) {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String userType = request.getParameter("userType");
            User user = userService.signup(username, email, password, userType);

            if (user != null) {
                response.sendRedirect(request.getContextPath() + "src/main/webapp/views/login.html");
            } else {
                // Handle failure to sign up
            }
        }
    }
}
