package com.kaizen.section5_radi.controller;

import com.kaizen.section5_radi.service.ProductService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet("/admin/add-product")
public class AddProductServlet extends HttpServlet {
    private ProductService productService;
    public AddProductServlet(){
        productService = new ProductService();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name= request.getParameter("name");
        String image=  request.getParameter("image");
        float price= Float.parseFloat(request.getParameter("price"));
        System.out.println("name:"+name+" ,price:"+price+" ,image:"+image);
        boolean added= productService.addProduct(name,price,image);
        if(added){
            System.out.println("Product Added successfully");
            response.sendRedirect(request.getContextPath() + "/add-product.jsp?message=Product added successfully!");
        }else{
            System.out.println("Failed to add product");
            response.sendRedirect(request.getContextPath() + "/add-product.jsp?error= Failed to add product");
        }
    }
    }
