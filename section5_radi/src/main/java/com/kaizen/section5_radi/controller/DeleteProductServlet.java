package com.kaizen.section5_radi.controller;

import com.kaizen.section5_radi.service.ProductService;
import com.kaizen.section5_radi.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/admin/delete-product")
public class DeleteProductServlet extends HttpServlet {
    private ProductService productService;
    private UserService userService;
    public DeleteProductServlet(){
        productService = new ProductService();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String sid= request.getParameter("id");
        int id=Integer.parseInt(sid);
        boolean deleted= false;
        try {
            productService.deleteProductById(id);
            deleted= true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (deleted) {
            request.setAttribute("Message","Product deleted successfully" );
            System.out.println("Product deleted successfully");
            response.sendRedirect(request.getContextPath() + "/ProductsMain");
        }else{
            System.out.println("Failed to delete product");
        }
    }
    }
