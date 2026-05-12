package com.kaizen.section5_radi.helper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaizen.section5_radi.model.Product;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDB {
    public static List<Product> getProduct(String user) throws ClassNotFoundException, SQLException {
        Jedis jedis = new Jedis("localhost", 6379);
        // per user (better than global)
        String key = "rate:" + user;
//        String key = "rate:" + email;
        int count = 0;
        String value = jedis.get(key);
        if (value != null) {
            count = Integer.parseInt(value);
        }
        // block if too many requests
        if (count >= 5) {
            throw new RuntimeException("Too many requests");
        }

        // increase counter
        jedis.incr(key);

        // reset after 10 seconds windo
        jedis.expire(key, 10);
        Gson gson = new Gson();
        String cached = jedis.get("products");
        if (cached != null) {
            System.out.println(">>> FROM REDIS CACHE");
            Type type = new TypeToken<ArrayList<Product>>() {
            }.getType();

            // "["id": 3 , "name": note , "price":40]"

            return gson.fromJson(cached, type);
        }
        System.out.println("Hitting the db..........");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//----------------------------------------------------------------------
        String url= "jdbc:mysql://localhost:3306/e_commerce";
        String username= "root";
        String password= "Sillysql1!";
        ArrayList<Product> products= new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection connection= DriverManager.getConnection(url,username,password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from product");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                float price = resultSet.getFloat("price");
                String name = resultSet.getString("name");
                String image = resultSet.getString("image");
                products.add(new Product(id, name, price, image));
            }
        }
//----------------------------------------------------------------------
        String json = gson.toJson(products);
        jedis.setex("products", 60, json);

        return products;
    }
    public static boolean validateUser(String username, String password) throws Exception {

        String url = "jdbc:mysql://localhost:3306/e_commerce";
        String dbUser = "root";
        String dbPassword = "Sillysql1!";

        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT * FROM user WHERE username=? AND password=?")) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }
}
