package com.ecommerceapp.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.sql.ResultSetMetaData;

import com.ecommerceapp.utility.DatabaseManager;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/GetSingleProductServlet")
public class GetSingleProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int productId = Integer.parseInt(request.getParameter("ID"));

        Connection connection = DatabaseManager.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Products WHERE ID = ?");
            preparedStatement.setInt(1, productId);

            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()) {
                String json = resultSetToJson(resultSet);
                System.out.println("prod"+json);

                // Send JSON response
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetSingleProductServlet.class.getName()).log(Level.SEVERE, null, ex);
            // Handle the error
        }
    }

    private String resultSetToJson(ResultSet resultSet) throws SQLException {
        JsonObject jsonObject = new JsonObject();

        ResultSetMetaData metadata = resultSet.getMetaData();
        int columnCount = metadata.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = metadata.getColumnName(i);
            jsonObject.addProperty(columnName, resultSet.getString(i));
        }

        return jsonObject.toString();
    }
}
