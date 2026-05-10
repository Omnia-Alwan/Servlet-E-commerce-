package com.kaizen.section5_radi.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kaizen.section5_radi.helper.ProductDB;
import com.kaizen.section5_radi.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;
    public LoginServlet() {
        this.userService = new UserService();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        System.out.println(email+" "+password);
        try {
            if (userService.validateUser(email, password)) {
                System.out.println("Successful login, Hi"+ email);
                //create session id
                String sessionId = java.util.UUID.randomUUID().toString();

                redis.clients.jedis.Jedis jedis =
                        new redis.clients.jedis.Jedis("localhost", 6379); //connect to redis
                //set session with session:sessionId as key and email as value in redis
                jedis.setex("session:" + sessionId, 300, email);

                //create cookie with sessionId
                Cookie cookie = new Cookie("SESSION_ID", sessionId);
                //cookie is sent by browser to all pages
                cookie.setPath("/");
                //attach cookie to response
                response.addCookie(cookie);
                //redirect to ProductsMain
                response.sendRedirect("ProductsMain");

            } else {
                response.setStatus(401);
                request.getRequestDispatcher("login.jsp").forward(request, response);
                response.getWriter().println("INVALID USER");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
/*
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    String email = request.getParameter("email");
    String password = request.getParameter("password");
    System.out.println(email+" "+password);
    try {
        if (userService.validateUser(email, password)) {
            // session
//                String sessionId = java.util.UUID.randomUUID().toString();
//
//                redis.clients.jedis.Jedis jedis =
//                        new redis.clients.jedis.Jedis("localhost", 6379);
//
//                jedis.setex("session:" + sessionId, 300, username);
//
//                Cookie cookie = new Cookie("SESSION_ID", sessionId);
//                cookie.setPath("/");
//                response.addCookie(cookie);

            response.sendRedirect("ProductsMain");


            // jwt

//                String token = JWT.create()
//                        .withClaim("user", username)
//                        .sign(Algorithm.HMAC256("secret")); // impo
//
//
//                response.sendRedirect("ProductsMain?token=" + token);

        } else {
            response.setStatus(401);
            response.getWriter().println("INVALID USER");
        }

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}
}
*/
