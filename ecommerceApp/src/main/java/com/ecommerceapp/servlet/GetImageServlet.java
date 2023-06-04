package com.ecommerceapp.servlet;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.ecommerceapp.utility.DatabaseManager;

@WebServlet("/getImageServlet")
public class GetImageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String productId = request.getParameter("ID");

        if (productId != null) {
            Connection connection = DatabaseManager.getConnection();

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT product_image FROM Products WHERE ID = ?");
                preparedStatement.setInt(1, Integer.parseInt(productId));

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    Blob image = resultSet.getBlob("product_image");
                    int blobLength = (int) image.length();
                    
                    byte[] blobAsBytes = image.getBytes(1, blobLength);
                    

                    //when the blob object is no longer needed
                    image.free();

                    response.setContentType("image/jpeg"); // adjust this to match the image's original format
                    response.getOutputStream().write(blobAsBytes);
                    response.getOutputStream().flush();  
                }
            } catch (SQLException ex) {
                Logger.getLogger(GetImageServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    
}
