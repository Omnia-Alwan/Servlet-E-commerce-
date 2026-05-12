package com.kaizen.section5_radi.controller;

import com.kaizen.section5_radi.model.Review;
import com.kaizen.section5_radi.service.ReviewService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

//@WebServlet("/product-reviews")
public class ProductReviewsServlet extends HttpServlet {
    private ReviewService reviewService;
    public ProductReviewsServlet(){
        reviewService = new ReviewService();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int productId = Integer.parseInt(request.getParameter("productId"));

        try {
            List<Review> reviewList= reviewService.getAllProductReviews(productId);
            request.setAttribute("reviews", reviewList);
            RequestDispatcher rs = request.getRequestDispatcher("product-details.jsp");
            //transfer request to product-details.jsp
            rs.forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    }
