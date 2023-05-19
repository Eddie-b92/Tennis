package com.tennis.controller;

import com.tennis.DAOFactory;
import com.tennis.Dispatcher;
import com.tennis.beans.User;
import com.tennis.models.UserDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "Login", value = "/login")
public class LoginServlet extends HttpServlet {

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

        HttpSession session = request.getSession(false);
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache"); // used for older browsers
        response.setDateHeader("Expires", 0); // expiration of the session

        if (session != null && session.getAttribute("username") != null)
            response.sendRedirect("player");
        else
            dispatcher.dispatch(request, response, "WEB-INF/pages/login.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //TODO: finish the login servlet
        // restrict access to users
        // get users profile number and limit the access that way
        // if user logged in, save the session

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username.equals("")     || password.equals("") ||
            username.length() > 100 || password.length() > 100) {
            request.setAttribute("error", "Identifiants incorrects");
            dispatcher.dispatch(request, response, "WEB-INF/pages/login.jsp");
            return;
        }

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        User userTryingToLogin = new User(username, password);
        User userConfirmed     = userDAO.login(userTryingToLogin);

        HttpSession session = null;
        if (userConfirmed != null) {
            session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("permission", userConfirmed.profile());
            response.sendRedirect("player");
        } else {
            request.setAttribute("error", "Identifiants incorrects");
            dispatcher.dispatch(request, response, "WEB-INF/pages/login.jsp");
        }

    }
}


