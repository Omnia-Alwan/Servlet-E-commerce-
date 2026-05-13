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
import java.time.Instant;
import java.time.LocalDateTime;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;
    public LoginServlet() {
        this.userService = new UserService();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        boolean rememberMe = Boolean.parseBoolean(request.getParameter("remember"));
        System.out.println(email+" "+password);
        try {
            if (userService.validateUser(email, password)) {
                System.out.println("Successful login, Hi"+ email);
                //create session id
                String sessionId = java.util.UUID.randomUUID().toString();

                redis.clients.jedis.Jedis jedis =
                        new redis.clients.jedis.Jedis("localhost", 6379); //connect to redis
                //set session:sessionId as key and email as value in redis
                jedis.setex("session:" + sessionId, 500, email);

                //create cookie with sessionId
                Cookie cookie = new Cookie("SESSION_ID", sessionId);
                //cookie is sent by browser to all pages
                cookie.setPath("/");
                //attach cookie to response -> server sends cookie to client
                response.addCookie(cookie);

                if(rememberMe) {
                    //TOKENS --> to remember me
                    String token = JWT.create()
                            .withClaim("email", email)
                            .withExpiresAt(Instant.now().plusSeconds(30 * 24 * 60 * 60))
                            .sign(Algorithm.HMAC256("secret")); // impo

                    Cookie rememberCookie= new Cookie("REMEMBER_ME", token);
                    rememberCookie.setPath("/");
                    rememberCookie.setMaxAge(30 * 24 * 60 * 60);
                    response.addCookie(rememberCookie);
                }


                //redirect to ProductsMain based on role
                if (userService.isAdmin(email)) {
                    response.sendRedirect(request.getContextPath() + "/ProductsMain");
                } else {
                    response.sendRedirect(request.getContextPath() + "/ProductsMain");
                }

            } else {
                response.setStatus(401);
                request.setAttribute("error", "Invalid email or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
