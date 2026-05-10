package com.kaizen.section5_radi.service;

import com.kaizen.section5_radi.dao.ProductDao;
import com.kaizen.section5_radi.model.Product;
import com.kaizen.section5_radi.model.Role;
import com.kaizen.section5_radi.model.User;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private ProductDao productDao;
    public ProductService(){
        productDao = new ProductDao();
    }
    public Product getProductById(int id) throws SQLException {
        Product product= productDao.getProductById(id);
        if(product!=null){
            return product;
        }else {
            throw new RuntimeException("No such Product Found");
        }
    }
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products= productDao.getAllProducts();
        if(products.isEmpty()){
            System.out.println("No products found");
        }
        return products;
    }
    public boolean deleteProductById(int id) throws SQLException {
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
        return deleted;
    }
    public boolean addProduct(String name, float price, String image){

        if(name.isEmpty() || image.isEmpty()){
            throw new RuntimeException("No field should be empty");
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
        return saved;
    }
}
