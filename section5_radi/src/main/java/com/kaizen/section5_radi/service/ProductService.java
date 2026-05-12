package com.kaizen.section5_radi.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaizen.section5_radi.dao.ProductDao;
import com.kaizen.section5_radi.model.Product;
import com.kaizen.section5_radi.model.Role;
import com.kaizen.section5_radi.model.User;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static redis.clients.jedis.Protocol.Command.TTL;

public class ProductService {
    private ProductDao productDao;
    public ProductService(){
        productDao = new ProductDao();
    }
    public Product getProductById(int id) throws SQLException, ClassNotFoundException {
        if(id<=0){
            throw new SQLException("Product ID is invalid");
        }
        Product product= productDao.getProductById(id);
        if(product!=null){
            return product;
        }else {
            throw new RuntimeException("No such Product Found");
        }
    }
    public List<Product> getAllProducts() throws SQLException, ClassNotFoundException {

        //1.connect
        Jedis jedis = new Jedis("localhost", 6379);
        Gson gson = new Gson();
        //2.check is products cached?
        String cached = jedis.get("products");
        if (cached != null) {
            System.out.println(">>> FROM REDIS CACHE");
            Type type = new TypeToken<ArrayList<Product>>() {
            }.getType();
            return gson.fromJson(cached, type);
        }
        //3.if not cached then call db and cache them
        System.out.println("Hitting the db..........");
        List<Product> products= productDao.getAllProducts();
        jedis.setex("products", 500, gson.toJson(products));
        if(products.isEmpty()){
            System.out.println("No products found");
        }
        return products;
    }
    public boolean deleteProductById(int id) throws SQLException, ClassNotFoundException {
        if(id<=0){
            throw new SQLException("Product ID is invalid");
        }
        Product product= productDao.getProductById(id);
        if(product == null){
            throw new RuntimeException("Product not found");
        }
        boolean deleted=false;
        try {
            productDao.deleteById(id);
            deleted=true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //invalidate cached products to retrieve updated products from db
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.del("products");
        System.out.println("ProductService addProduct: Invalidated products, next time hit db");

        return deleted;
    }
    public boolean addProduct(String name, float price, String image){

        if(name.isEmpty() || image.isEmpty()){
            throw new RuntimeException("No field should be empty");
        }
        if(price <= 0){
            throw new RuntimeException("Price should be greater than 0");
        }
        Product newProduct= new Product(name,price,image);
        boolean saved= false;
        try {
            productDao.saveProduct(newProduct);
            saved=true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //invalidate cached products to retrieve updated products from db
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.del("products");
        System.out.println("ProductService deleteProduct: Invalidated products, next time hit db");
        return saved;
    }
}
