package com.kaizen.section5_radi.filter;

import com.kaizen.section5_radi.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static jakarta.servlet.http.HttpServletResponse.*;

@WebFilter(value = "/*", filterName = "RateLimitFilter")
public class RateLimitFilter implements Filter {

    static final Pattern IP_PATTERN = Pattern.compile("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)[.]){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String userId = (String) request.getAttribute("email");


        //in case of login we don't have email so assign IP address
        if(userId == null){
            userId= request.getRemoteAddr();
        }
        String path = request.getRequestURI()
                .substring(request.getContextPath().length());

        System.out.println("REQUEST from RL: " + path);
        String operation="";
        if (path.contains("logout")) {
            chain.doFilter(request, response);
            return;
        }
        //limit requests to login, register, addReview servlets
        if (path.contains("login.jsp") || path.contains("/login")) {
            operation = "login";
        } else if (path.contains("register.jsp") || path.contains("/register")){
            operation = "register";
        }else if (path.contains("add-review") && !matchIP(userId)) {
            operation = "add-review";
        } else {
            chain.doFilter(request, response);
            return;
        }
        
        Jedis jedis = new Jedis("localhost", 6379);

        // per user (better than global)
        String key = "rate:" + userId + ":" + operation;

        int count;
        String value = jedis.get(key);
        if (value == null) {
            jedis.setex(key, 60, "1");
            count = 1;
        } else {
            count = Integer.parseInt(value);
        }

        // Block if too many requests (5 or more)
        if (count >= 5) {
            response.sendError(SC_BAD_REQUEST, "Too many requests. Try again later.");
            return;
        }

        System.out.println("number of requests to "+operation + " is : " + jedis.get(key) +", from user: "+userId);
        // increase counter
        if (value != null) {
            jedis.incr(key);
        }
        jedis.close();
        chain.doFilter(request, response);
    }

    static boolean matchIP(String input) {
        Matcher matcher = IP_PATTERN.matcher(input); //see if userId is an IP
        return matcher.matches(); //return true if it's an IP, false otherwise
    }
    }
