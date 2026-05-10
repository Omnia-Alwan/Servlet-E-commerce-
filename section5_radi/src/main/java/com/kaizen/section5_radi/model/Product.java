package com.kaizen.section5_radi.model;

public class Product {
    private int id;
    private String name;
    private float price;
    private String image;

    public Product(int id, String name, float price, String img) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = img;
    }
    public Product() {}

    public Product(String name, float price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
