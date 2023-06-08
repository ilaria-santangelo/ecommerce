package com.ecommerceapp.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ecommerceapp.utility.DatabaseManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.sql.ResultSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.sql.ResultSetMetaData;
import java.io.InputStream;
import java.io.PrintWriter;

import jakarta.servlet.annotation.MultipartConfig;

@WebServlet("/ProductServlet")
@MultipartConfig(maxFileSize = 16177216)
public class ProductServlet extends HttpServlet {

    PrintWriter out;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");

        String productName = request.getParameter("productName");
        String productDescription = request.getParameter("productDescription");
        double productPrice = Double.parseDouble(request.getParameter("productPrice"));
        String productCategory = request.getParameter("productCategory");

        Part filePart = request.getPart("productImage");

        if(filePart != null) {
            try{
                Connection connection = DatabaseManager.getConnection();

                String sqlQuery = String.format(
                        "INSERT INTO Products (vendor_id, product_name, product_description, product_price, product_category, product_image) VALUES (%d, '%s', '%s', %f, '%s', '%s')",
                        userId, productName, productDescription, productPrice, productCategory, filePart.toString());

                Statement statement = connection.createStatement();

                statement.executeUpdate(sqlQuery);

                // Redirect to the vendor profile page or a success page
                response.sendRedirect(request.getContextPath() + "/src/main/webapp/views/profile.html");
            } catch (SQLException ex) {
                Logger.getLogger(ProductServlet.class.getName()).log(Level.SEVERE, null, ex);
                response.sendRedirect(request.getContextPath() + "/src/main/webapp/views/profile.html");
            }
        }
    }
}
