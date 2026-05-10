package com.kaizen.section5_radi.controller;

import com.kaizen.section5_radi.service.ProductService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/delete-product")
public class DeleteProductServlet extends HttpServlet {
    private ProductService productService;
    public DeleteProductServlet(){
        productService = new ProductService();
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id=Integer.parseInt(request.getParameter("id"));
        boolean deleted= false;
        try {
            productService.deleteProductById(id);
            deleted= true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (deleted) {
            request.setAttribute("Message","Product deleted successfully" );
            System.out.println("Product deleted successfully");
        }else{
            System.out.println("Failed to delete product");
        }
    }
    }
