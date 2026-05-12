package com.kaizen.section5_radi.model;

import java.time.LocalDate;

public class Review {
    private int id;
    private String body;
    private int stars;
    private int userId;
    private String username;
    private int productId;
    private LocalDate createdAt;

    public Review() {}
    public Review(int id, String body, int stars,String username, int userId, int productId) {
        this.id = id;
        this.body = body;
        this.stars = stars;
        this.userId = userId;
        this.username = username;
        this.productId = productId;
        this.createdAt = LocalDate.now();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Review(String body, int stars, int userId, int productId) {

        this.body = body;
        this.stars = stars;
        this.userId = userId;
        this.productId = productId;
        this.createdAt = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
