package com.kaizen.section5_radi.dao;

import com.kaizen.section5_radi.model.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao {
    private String url= "jdbc:mysql://localhost:3306/e_commerce";
    private String username= "root";
    private String password= "Sillysql1!";

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url,username,password);
    }
    public boolean saveReview(Review review) throws SQLException, ClassNotFoundException {

        try(Connection connection= getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO review (body, stars, userId, productId) VALUES (?,?,?,?)")){

            ps.setString(1, review.getBody());
            ps.setInt(2, review.getStars());
            ps.setInt(3, review.getUserId());
            ps.setInt(4, review.getProductId());
            int affectedRows = ps.executeUpdate();
            return affectedRows>0;
        }
    }
    public List<Review> getProductReviews(int productId) throws SQLException, ClassNotFoundException {
        try(Connection connection= getConnection();
            PreparedStatement ps = connection.prepareStatement
                    ("SELECT r.*, u.username FROM review r" +
                    " JOIN user u ON r.userId = u.id" +
                    " WHERE r.productId = ?" +
                    " ORDER BY r.created_at DESC")) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            List<Review>  reviews = new ArrayList<>();
            while(rs.next()){
                Review review = new Review();
                review.setId(rs.getInt("id"));
                review.setBody(rs.getString("body"));
                review.setStars(rs.getInt("stars"));
                review.setUserId(rs.getInt("userId"));
                review.setUsername(rs.getString("username"));
                review.setProductId(rs.getInt("productId"));
                review.setCreatedAt(rs.getDate("created_at").toLocalDate());
                reviews.add(review);
            }
            return reviews;
        }
    }
}
