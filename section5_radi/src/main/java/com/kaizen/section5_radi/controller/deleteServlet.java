package com.kaizen.section5_radi.controller;

import com.kaizen.section5_radi.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/delete")
public class deleteServlet extends HttpServlet {

    private UserService userService;

    public deleteServlet() {
        this.userService = new UserService();
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String sid= request.getParameter("id");
        int id=Integer.parseInt(sid);
        try {
            if (userService.deleteUserById(id)){
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
