package com.kaizen.section5_radi.service;

import com.kaizen.section5_radi.dao.ReviewDao;
import com.kaizen.section5_radi.dao.UserDao;
import com.kaizen.section5_radi.model.Product;
import com.kaizen.section5_radi.model.Review;
import com.kaizen.section5_radi.model.User;

import java.sql.SQLException;
import java.util.List;

public class ReviewService {
    private ReviewDao reviewDao;
    private UserDao userDao;
    public ReviewService(){
        reviewDao = new ReviewDao();
        userDao = new UserDao();
    }
    public boolean addReview(String body, int stars, String userEmail, int productId) throws SQLException, ClassNotFoundException {

        if(body == null || body.trim().isEmpty()){
            throw new RuntimeException("Review body cannot be empty");
        }
        if(stars < 1 || stars > 5){
            throw new RuntimeException("Stars must be between 1 and 5");
        }
        User user= userDao.findByEmail(userEmail);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        Review newReview= new Review(body, stars, user.getId(), productId);
        boolean saved= false;
        try {
            reviewDao.saveReview(newReview);
            saved=true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return saved;
    }
    public List<Review> getAllProductReviews(int productId) throws SQLException, ClassNotFoundException {
        List<Review> productReviews= reviewDao.getProductReviews(productId);
        if(productReviews.isEmpty()){
            System.out.println("No reviews found for this product");
        }
        return productReviews;
    }
}
