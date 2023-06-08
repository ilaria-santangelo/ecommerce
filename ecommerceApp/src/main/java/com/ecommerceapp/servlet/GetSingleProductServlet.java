package com.ecommerceapp.servlet;

import com.ecommerceapp.service.ProductService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import java.io.IOException;

@WebServlet("/GetSingleProductServlet")
public class GetSingleProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String productId = request.getParameter("ID");

        ProductService productService = new ProductService();
        String json = productService.getSingleProduct(productId);

        if(json != null) {
            // Send JSON response
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } else {
            // handle the situation when no product was found for the provided ID
            // this could be sending an error response or redirecting to a 'product not found' page.
        }
    }
}
