package com.kaizen.section5_radi.dao;

import com.kaizen.section5_radi.model.Product;
import com.kaizen.section5_radi.model.Role;
import com.kaizen.section5_radi.model.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDao {
    private String url= "jdbc:mysql://localhost:3306/e_commerce";
    private String username= "root";
    private String password= "Sillysql1!";

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url,username,password);
    }


    public User findByEmailAndPassword(String email, String password) throws SQLException, ClassNotFoundException {

        try(Connection connection= getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM user WHERE email=? AND password=?")) {

                ps.setString(1, email);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();
                User user = new User();
                if(rs.next()){
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setAddress(rs.getString("address"));
                }
                return user;
        }
    }
    public User findByEmail(String email) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection connection= getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM user WHERE email=? ")) {

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            User user = new User();
            while(rs.next()){
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setRole(Role.valueOf(rs.getString("role")));
            }
            return user;
        }
    }

    public User findById(int id) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection connection= getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM user WHERE id=? ")) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            User user = new User();
            while(rs.next()){
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
            }
            return user;
        }
    }
    public boolean save(User user) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        try(Connection connection= getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO user (username, password, email, phone, address) VALUES (?,?,?,?,?)")){

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getAddress());
            int affectedRows = ps.executeUpdate();
            return affectedRows>0;
        }
        }
    public boolean deleteById(int userId) throws SQLException, ClassNotFoundException {
        try(Connection connection= getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM user WHERE id=?")){
            ps.setInt(1, userId);
            int affectedRows = ps.executeUpdate();
            return affectedRows>0;
        }
    }
    public boolean emailExists(String email) throws SQLException, ClassNotFoundException {
        try(Connection connection= getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM user WHERE email=?")){
            ps.setString(1, email);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return rs.getInt(1)>0;
                }
            }
        }
        return false;
    }
}
