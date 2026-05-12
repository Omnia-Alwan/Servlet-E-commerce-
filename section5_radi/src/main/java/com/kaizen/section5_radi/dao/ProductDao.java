package com.kaizen.section5_radi.dao;

import com.kaizen.section5_radi.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private String url= "jdbc:mysql://localhost:3306/e_commerce";
    private String username= "root";
    private String password= "Sillysql1!";

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url,username,password);
    }
    public boolean saveProduct(Product product) throws SQLException, ClassNotFoundException {


        try(Connection connection= getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO product (name, price, image) VALUES (?,?,?)")){

            ps.setString(1, product.getName());
            ps.setFloat(2, product.getPrice());
            ps.setString(3, product.getImage());
            int affectedRows = ps.executeUpdate();
            return affectedRows>0;
        }
    }
    public boolean deleteById(int productId) throws SQLException, ClassNotFoundException {

        try(Connection connection= getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM product WHERE id=?")){
            ps.setInt(1, productId);
            int affectedRows = ps.executeUpdate();
            return affectedRows>0;
        }
    }
    public Product getProductById(int productId) throws SQLException, ClassNotFoundException {
        try(Connection connection= getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM product WHERE id=?")) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            Product product = null;
            if(rs.next()){
                product= new Product();
                product.setId(rs.getInt("id"));
                product.setImage(rs.getString("image"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getFloat("price"));
            }
            return product;
        }
    }
    public List<Product> getAllProducts() throws SQLException, ClassNotFoundException {
        try(Connection connection= getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM product")) {
            ResultSet rs = ps.executeQuery();
            List<Product> products= new ArrayList<>();
            while(rs.next()){
                Product product= new Product();
                product.setId(rs.getInt("id"));
                product.setImage(rs.getString("image"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getFloat("price"));
                products.add(product);
            }
            return products;
        }
    }
}
