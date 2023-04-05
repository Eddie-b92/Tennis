package com.tennis.controller.player;

import com.tennis.DAOFactory;
import com.tennis.beans.Player;
import com.tennis.Dispatcher;
import com.tennis.models.PlayerDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "PlayerServlet", value = "/player")
public class PlayerServlet extends HttpServlet {

    private PlayerDAO playerDAO;
    private Player player;
    private Dispatcher dispatcher = new Dispatcher();
    private HttpSession session   = null;

    // Gets us the connection from DAOFactory instance method when servlet gets initialized
    @Override
    public void init() throws ServletException {
        var daoFactory = DAOFactory.getInstance();
        this.playerDAO = daoFactory.getPlayerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        session = request.getSession(false);

        if (session != null && session.getAttribute("username") != null)
            dispatcher.dispatch(request, response, "WEB-INF/pages/player.jsp");
        else
            response.sendRedirect("login");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login");
            return;
        }

        player = null;
        String value = request.getParameter("button");
        try {
            int permission   = (int) session.getAttribute("permission");
            String firstName = request.getParameter("firstName");
            String lastName  = request.getParameter("lastName");
            String gender    = getGender(request);

            if (firstName.length() > 20 ||
                lastName .length() > 20 ||
                gender   .length() > 1) {

                request.setAttribute("error", "Requête invalide!");
                dispatcher.dispatch(request, response, "/WEB-INF/pages/player.jsp");
                return;
            }

            if ("add".equals(value)
            && permission == 1) {
                if (firstName.equals("") ||
                    lastName .equals("") ||
                    gender   .equals("")) {
                    request.setAttribute("error", "Requête invalide!");
                    dispatcher.dispatch(request, response, "/WEB-INF/pages/player.jsp");
                } else
                    add(request, response, firstName, lastName, gender);

            } else if ("search".equals(value)) {

                search(request, response, firstName, lastName, gender);

            } else response.sendRedirect("/WEB-INF/pages/error.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/WEB-INF/pages/error.jsp");
        }

    }

    private void add(HttpServletRequest request,
                     HttpServletResponse response,
                     String firstName,
                     String lastName,
                     String gender) throws Exception {
        player = new Player(firstName, lastName, gender);

        request.setAttribute("playerAdded", playerDAO.add   (player));
        request.setAttribute("players",     playerDAO.search(player));

        dispatcher.dispatch(request, response, "/WEB-INF/pages/player.jsp");
    }

    private void search(HttpServletRequest request,
                        HttpServletResponse response,
                        String firstName,
                        String lastName,
                        String gender) throws Exception {

        player = new Player(firstName, lastName, gender);
        request.setAttribute("players", playerDAO.search(player));

        dispatcher.dispatch(request, response, "/WEB-INF/pages/player.jsp");
    }

    private String getGender(HttpServletRequest request) {
        String[] gender = request.getParameterValues("gender");
        if (gender == null)    return "";
        if (gender.length > 1) return "";
        else
            switch (gender[0]) {
                case "H" -> {return "H";}
                case "F" -> {return "F";}
                default  -> {return "" ;}
            }
    }
}


















