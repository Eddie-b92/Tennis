package com.tennis.controller;

import com.tennis.DAOFactory;
import com.tennis.Dispatcher;
import com.tennis.models.UserDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {
    private UserDAO userDAO;
    private Dispatcher dispatcher = new Dispatcher();

    // Gets us the connection from DAOFactory instance method when servlet gets initialized
    @Override
    public void init() throws ServletException {
        var daoFactory = DAOFactory.getInstance();
        this.userDAO = daoFactory.getUserDAO();
    }

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
        dispatcher.dispatch(request, response, "WEB-INF/pages/logout.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String value = request.getParameter("button");
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            if ("Yes".equals(value)) {
                session.invalidate();

                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                response.setHeader("Pragma", "no-cache");
                response.setDateHeader("Expires", 0);

                response.sendRedirect("login");
            } else if ("No".equals(value))
                response.sendRedirect("player");
            else
                response.sendRedirect("error");
        } else
            response.sendRedirect("login");
    }
}
