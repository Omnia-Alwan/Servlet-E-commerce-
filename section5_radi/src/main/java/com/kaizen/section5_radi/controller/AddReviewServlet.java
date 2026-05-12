package com.kaizen.section5_radi.controller;

import com.kaizen.section5_radi.service.ReviewService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/add-review")
public class AddReviewServlet extends HttpServlet {
    private ReviewService reviewService;
    public AddReviewServlet() {
        reviewService = new ReviewService();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String body= request.getParameter("body");
        int stars= Integer.parseInt(request.getParameter("stars"));
        String userEmail= request.getAttribute("email").toString();
        int productId= Integer.parseInt(request.getParameter("productId"));
        System.out.println("Review body: "+body+" Review rating: "+stars+" Review productId: "+productId+" UserEmail: "+userEmail);

        boolean added= false;
        try {
            added= reviewService.addReview(body, stars, userEmail, productId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found in add review");
            throw new RuntimeException(e);
        }
        if(added){
            System.out.println("Review Added successfully");
            response.sendRedirect(request.getContextPath() + "/product-details?id="+productId+
                    "&message=Review+added+successfully");
        }else{
            System.out.println("Failed to add product");
            response.sendRedirect(request.getContextPath() + "/product-details?id="+productId+
                    "&error=Failed+to+add+review");
        }
    }
    }
