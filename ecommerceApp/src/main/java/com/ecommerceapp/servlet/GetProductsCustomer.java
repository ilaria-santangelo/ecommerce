package com.ecommerceapp.servlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ecommerceapp.utility.DatabaseManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.ResultSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.sql.ResultSetMetaData;

@WebServlet("/GetProductsCustomer")
public class GetProductsCustomer extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");

        Connection connection = DatabaseManager.getConnection();
        try {
            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM Products";
            ResultSet resultSet = statement.executeQuery(sql);
            String json = resultSetToJson(resultSet);
            
            // Send JSON response
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (SQLException ex) {
            Logger.getLogger(GetProductServlet.class.getName()).log(Level.SEVERE, null, ex);
            // Handle the error
        }
    }

    private String resultSetToJson(ResultSet resultSet) throws SQLException {
        JsonArray jsonArray = new JsonArray();
    
        ResultSetMetaData metadata = resultSet.getMetaData();
        int columnCount = metadata.getColumnCount();
    
        while (resultSet.next()) {
            JsonObject jsonObject = new JsonObject();
            
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metadata.getColumnName(i);
                jsonObject.addProperty(columnName, resultSet.getString(i));
            }
    
            jsonArray.add(jsonObject);
        }
    
        return jsonArray.toString();
    }
}
