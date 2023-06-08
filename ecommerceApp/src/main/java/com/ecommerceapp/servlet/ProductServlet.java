package com.ecommerceapp.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ecommerceapp.service.ProductService;
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

    private ProductService productService = new ProductService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int vendorId = (int) session.getAttribute("userId");

        String name = request.getParameter("productName");
        String description = request.getParameter("productDescription");
        double price = Double.parseDouble(request.getParameter("productPrice"));
        String category = request.getParameter("productCategory");
        Part imagePart = request.getPart("productImage");
        String image = imagePart.toString();

        productService.addProduct(vendorId, name, description, price, category, image);

        response.sendRedirect(request.getContextPath() + "/src/main/webapp/views/profile.html");
    }
}
