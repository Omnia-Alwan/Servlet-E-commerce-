package com.kaizen.section5_radi.filter;

import com.kaizen.section5_radi.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebFilter(value = "/admin/*", filterName = "AuthorizeFilter")
public class AuthorizeFilter implements Filter {
    private UserService userService;
    public AuthorizeFilter(){
        userService=new UserService();
    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String email = (String) request.getAttribute("email");
        //make sure user is loged in
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        try {
            if(userService.isAdmin(email)){
                System.out.println("Hello admin: "+email);
                chain.doFilter(request, response);
            }else{
                System.out.println("Only admin are allowed product management" +
                        "\nRedirect to ProductsMain");
                //averge user redirect to productmain
                response.sendRedirect(request.getContextPath() + "/ProductsMain");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        /**/
    }
}
