package com.kaizen.section5_radi.controller;

import com.kaizen.section5_radi.model.Product;
import com.kaizen.section5_radi.service.ProductService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/product-details")
public class ViewProductServlet extends HttpServlet {
    private ProductService productService;
    public ViewProductServlet(){
        productService = new ProductService();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id= Integer.parseInt(request.getParameter("id"));

        try {
            Product product = productService.getProductById(id);
            request.setAttribute("product", product);
            RequestDispatcher rs = request.getRequestDispatcher("product-details.jsp");
            //transfer request to product-details.jsp
            rs.forward(request, response);

        } catch (RuntimeException | SQLException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }
    }
