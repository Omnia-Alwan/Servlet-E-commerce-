package com.kaizen.section5_radi.controller;

import com.kaizen.section5_radi.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService;
    public RegisterServlet() {
        this.userService = new UserService();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmedPassword = request.getParameter("confirmedPassword");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        //System.out.println(username + password + confirmedPassword + email + phone + address  );
        boolean registered= false;
        try {
            registered = userService.registerUser(username, email, password, confirmedPassword, phone, address);
        } catch (SQLException e) {
            System.out.println("failed to register");
            throw new RuntimeException(e);
        }
        if(registered){
            System.out.println("User registered successfully, redirect to login page");
            response.sendRedirect("login.jsp");
        }
        else{
            System.out.println("Failed to register user");
            //response.sendRedirect(request.getContextPath() + "/register.jsp?error=Registration failed. Please try again.");
        }

    }
    }
