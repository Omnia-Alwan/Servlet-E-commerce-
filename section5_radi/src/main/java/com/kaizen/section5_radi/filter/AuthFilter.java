package com.kaizen.section5_radi.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import redis.clients.jedis.Jedis;

import java.io.IOException;

@WebFilter(value = "/*", filterName = "AuthFilter")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI()
                .substring(request.getContextPath().length());

        System.out.println("REQUEST: " + path);

        if (path.contains("login.jsp") || path.contains("/login") || path.contains("register.jsp") || path.contains("/register")) {
            chain.doFilter(request, response);
            return;
        }
        String email = null;
        // session based
        String sessionId = null;  //random value

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("SESSION_ID".equals(c.getName())) {
                    sessionId = c.getValue();
                    break;
                }
            }
        }

        if (sessionId != null) {
            Jedis jedis = new Jedis("localhost", 6379); //connect to redis
            email = jedis.get("session:" + sessionId);
        }

        // jwt


        String token= null;
        if(email==null && cookies != null) {
            for (Cookie c : cookies) {
                if ("REMEMBER_ME".equals(c.getName())) {
                    token = c.getValue();
                }
            }
            if (token != null) {
                try {
                    DecodedJWT jwt = JWT.require(Algorithm.HMAC256("secret"))
                            .build()
                            .verify(token);

                    email = jwt.getClaim("email").asString();

                    //RECREATE SESSION
                    if(email!=null) {
                        //create session id
                        sessionId = java.util.UUID.randomUUID().toString();

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
                    }
                } catch (Exception e) {
                    email = null;
                }
            }
        }

        if (email == null) {
            System.out.println("UNAUTHORIZED REQUEST: " + path);
            response.sendRedirect("login.jsp");
            return;
        }
        //save email to use later for rate limiting
        request.setAttribute("email", email);
        //move to next filter
        chain.doFilter(request, response);
    }
}