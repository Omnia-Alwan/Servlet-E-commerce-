package com.kaizen.section5_radi.controller;

import com.kaizen.section5_radi.helper.ProductDB;
import com.kaizen.section5_radi.model.Product;
import com.kaizen.section5_radi.service.ProductService;
import com.kaizen.section5_radi.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ProductsMain")
public class ProductsMain extends HttpServlet {
    private ProductService productService;
    private UserService userService;
    public ProductsMain() {
        productService = new ProductService();
        userService = new UserService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        long start = System.currentTimeMillis();
        // model
        List <Product> products = null;
        try {
            //String user = (String) request.getAttribute("user"); //replace user with email that we set in AuthFilter
//            data = ProductDB.getProduct(user);
            products= productService.getAllProducts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        long end = System.currentTimeMillis();
        System.out.println("DB CALL TIME: " + (end - start) + " ms");
        request.setAttribute("products", products);
        System.out.println("Products found: " + (products == null ? "null" : products.size()));
        if (products != null && !products.isEmpty()) {
            System.out.println("First product: " + products.get(0).getName());
        }
        // view
        String email= request.getAttribute("email").toString();
        try {
            if(userService.isAdmin(email)){
                RequestDispatcher rs = request.getRequestDispatcher("Admin-ProductsMain.jsp");
                rs.forward(request,response);
            }else{
                RequestDispatcher rs = request.getRequestDispatcher("display-products.jsp");
                rs.forward(request,response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
