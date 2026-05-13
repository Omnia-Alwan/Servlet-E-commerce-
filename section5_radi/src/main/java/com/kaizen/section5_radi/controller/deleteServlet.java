package com.kaizen.section5_radi.controller;

import com.kaizen.section5_radi.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/delete")
public class deleteServlet extends HttpServlet {

    private UserService userService;

    public deleteServlet() {
        this.userService = new UserService();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String email=request.getAttribute("email").toString();

        try {
            if (userService.deleteUserByEmail(email)){
                String sessionId;
                for(Cookie c: request.getCookies()){
                    if(c.getName().equals("SESSION_ID")){
                        sessionId = c.getValue();

                        Jedis jedis = new Jedis("localhost", 6379);
                        jedis.del("session:" + sessionId);

                        c.setMaxAge(0);
                        response.addCookie(c);
                        c.setPath("/");
                    }
                    if (c.getName().equals("REMEMBER_ME")) {
                        c.setMaxAge(0);
                        c.setPath("/");
                        response.addCookie(c);
                    }
                }
                System.out.println("User deleted successfully");
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    }
