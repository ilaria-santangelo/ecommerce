package com.ecommerceapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ecommerceapp.utility.DatabaseManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        
        if ("login".equalsIgnoreCase(action)) {
            // Login
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            Connection connection = DatabaseManager.getConnection();
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Users WHERE email = '" + email + "' AND Password = '" + password + "'");

                if (resultSet.next()) {
                    out.write("success");
                    int generatedId = resultSet.getInt("ID");
                    HttpSession session = request.getSession();
                    session.setAttribute("userId", generatedId);
                    String userType = resultSet.getString("UserType");
                    if ("vendor".equalsIgnoreCase(userType)) {
                        response.sendRedirect(request.getContextPath() + "src/main/webapp/views/profile.html");
                    } else if ("customer".equalsIgnoreCase(userType)) {
                        response.sendRedirect(request.getContextPath() + "src/main/webapp/views/customer.html");
                    } 
                } else {
                    out.write("failure");
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                out.write("error");
            } finally {
                out.close();
            }
        } else if ("signup".equalsIgnoreCase(action)) {
            // Signup
            String name = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String userType = request.getParameter("userType");
        
            Connection connection = DatabaseManager.getConnection();
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO Users(Username, email, Password, UserType) VALUES ('" + name + "','" + email + "','" + password + "','" + userType + "')", Statement.RETURN_GENERATED_KEYS);
                
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
        
                    if ("vendor".equalsIgnoreCase(userType)) {
                        // Insert the generated ID into the vendors table
                        statement.executeUpdate("INSERT INTO Vendors(user_id) VALUES ('" + generatedId + "')");
                    } else if ("customer".equalsIgnoreCase(userType)) {
                        // Insert the generated ID into the customers table
                        statement.executeUpdate("INSERT INTO Customers(user_id) VALUES ('" + generatedId + "')");
                    }
                }
                response.sendRedirect(request.getContextPath() + "src/main/webapp/views/login.html");
            } catch (SQLException ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("logout".equalsIgnoreCase(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "src/main/webapp/views/login.html");
        }
    }
}
