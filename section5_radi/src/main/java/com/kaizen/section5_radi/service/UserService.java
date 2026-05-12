package com.kaizen.section5_radi.service;

import com.kaizen.section5_radi.dao.UserDao;
import com.kaizen.section5_radi.model.Role;
import com.kaizen.section5_radi.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserService {
    private UserDao userDao;
    private String url= "jdbc:mysql://localhost:3306/e_commerce";
    private String dbUser= "root";
    private String dbPassword= "Sillysql1!";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,dbUser,dbPassword);
    }
    public UserService(){
        this.userDao= new UserDao();
    }
    public boolean registerUser(String username, String email, String password, String confirmPassword, String phone, String address) throws SQLException {

        if(phone.isEmpty() || address.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || username.isEmpty() || email.isEmpty()){
            throw new RuntimeException("No field should be empty");
        }
        if(!password.equals(confirmPassword)){
            throw new RuntimeException("Passwords don't match");
        }
        if(username.length() < 3 ){
            throw new RuntimeException("Username must contain at least 3 letters");
        }
        if(!email.contains("@")){
            throw new RuntimeException("Invalid email must contain @");
        }

        User newUser= new User(username,email,password, phone, address, Role.AVG_USER);
        boolean saved= false;
        try {
            userDao.save(newUser);
            saved=true;
            System.out.println("All good");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("Not so good");
            throw new RuntimeException(e);
        }
        return saved;
    }

    public boolean deleteUserByEmail(String email) throws SQLException, ClassNotFoundException {
        if(email == null || email.isEmpty()){
            throw new SQLException("User email is invalid");
        }
        User user= userDao.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        boolean deleted=false;
        try {
            userDao.deleteById(user.getId());
            deleted=true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return deleted;
    }

    public boolean validateUser(String email, String password) throws SQLException, ClassNotFoundException {
        if(email ==null || password ==null){
            throw new RuntimeException("Email or password is null");
        }
       User user= userDao.findByEmailAndPassword(email,password);
        System.out.println("User Id: " + user.getId());
        System.out.println("username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
       if(user.getId() == 0){

           return false;
       }
       return true;
    }
    public boolean isAdmin(String email) throws SQLException, ClassNotFoundException {
        User user= userDao.findByEmail(email);
        System.out.println("User Id: " + user.getId());
        System.out.println("username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Role: " + user.getRole().toString());
        if(user.getRole() == Role.ADMIN){
            return true;
        }
        return false;
    }

    public int getUserIdByEmail(String email) throws SQLException, ClassNotFoundException {
        User user= userDao.findByEmail(email);
        System.out.println("User Id: " + user.getId());
        System.out.println("username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Role: " + user.getRole().toString());
        return user.getId();
    }
}
