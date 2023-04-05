package com.tennis.controller;

import com.tennis.Dispatcher;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ErrorServlet", value = "/error")
public class ErrorServlet extends HttpServlet {
    private Dispatcher dispatcher = new Dispatcher();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login");
            return;
        }
        dispatcher.dispatch(request, response, "WEB-INF/pages/error.jsp");
    }

}
