package com.tennis.controller.player;

import com.tennis.DAOFactory;
import com.tennis.Dispatcher;
import com.tennis.beans.Player;
import com.tennis.models.PlayerDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ModifyPlayerServlet", value = "/modifyPlayer")
public class ModifyPlayerServlet extends HttpServlet {

    private PlayerDAO playerDAO;
    private Player player;
    private Dispatcher dispatcher = new Dispatcher();

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

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login");
            return;
        } else if ((int)session.getAttribute("permission") != 1) {
            response.sendRedirect("player");
            return;
        }
        player = null;
        try {
            int playerId = Integer.parseInt(request.getParameter("modify"));
            if (playerId > 0 && playerId < 1_000_000) {
                player = playerDAO.getPlayer(playerId);

                request.setAttribute("firstName", player.firstName());
                request.setAttribute("lastName",  player.lastName());
                request.setAttribute("gender",    player.gender());

                dispatcher.dispatch(request, response, "/WEB-INF/pages/modifyPlayer.jsp");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dispatcher.dispatch(request, response, "/WEB-INF/pages/error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login");
            return;
        }
        try {
            String firstName =                request.getParameter("firstName");
            String lastName  =                request.getParameter("lastName") ;
            String gender    = getValidGender(request.getParameter("gender"))  ;

            if (firstName.equals("")    ||
                lastName .equals("")    ||
                gender   .equals("")    ||
                firstName.length() > 20 ||
                lastName .length() > 20 ||
                gender   .length() > 1 ) {
                dispatcher.dispatch(request, response, "/WEB-INF/pages/error.jsp");
                return;
            }

            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName" , lastName);
            request.setAttribute("gender"   , gender);

            if (player != null) {
                // Give player the updated params
                player.setFirstName(firstName);
                player.setLastName(lastName);
                player.setGender(gender);
                // Update table
                if (playerDAO.update(player)) {
                    // Update table
                    request.setAttribute("players", playerDAO.search(player));
                    dispatcher.dispatch(request, response, "/WEB-INF/pages/player.jsp");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dispatcher.dispatch(request, response, "/WEB-INF/pages/error.jsp");
        }
    }


    private String getValidGender(String gender) {
        switch (gender) {
            case "H" -> { return "H" ;}
            case "F" -> { return "F" ;}
            default  -> { return ""  ;}
        }
    }
}





























































































