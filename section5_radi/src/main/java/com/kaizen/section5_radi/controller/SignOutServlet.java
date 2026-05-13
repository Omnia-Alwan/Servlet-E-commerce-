package com.kaizen.section5_radi.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import redis.clients.jedis.Jedis;

import java.io.IOException;

@WebServlet("/logout")
public class SignOutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //get sessionId from cookie
        String sessionId = null;
        if(request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals("SESSION_ID")) {
                    sessionId = c.getValue();

                    Jedis jedis = new Jedis("localhost", 6379);
                    jedis.del("session:" + sessionId);

                    c.setMaxAge(0);
                    c.setPath("/");
                    response.addCookie(c);
                    jedis.close();
                }
                if (c.getName().equals("REMEMBER_ME")) {
                    c.setMaxAge(0);
                    c.setPath("/");
                    response.addCookie(c);

                }

            }
        }
        System.out.println("=== COOKIES AFTER LOGOUT ===");
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                System.out.println(c.getName() + " = " + c.getValue() + ", path: " + c.getPath());
            }
        }
        System.out.println("You've been logged out");
        //redirect
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
    }
