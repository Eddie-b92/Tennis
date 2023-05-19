package com.tennis.controller.player;

import com.tennis.DAOFactory;
import com.tennis.Dispatcher;
import com.tennis.beans.Player;
import com.tennis.models.PlayerDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "DeletePlayerServlet", value = "/deletePlayer")
public class DeletePlayerServlet extends HttpServlet {

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
            int playerId = Integer.parseInt(request.getParameter("delete"));
            if (playerId > 0 && playerId < 1_000_000) {
                player = playerDAO.getPlayerById(playerId);

                request.setAttribute("name", player.firstName() +" "+ player.lastName());

                dispatcher.dispatch(request, response, "/WEB-INF/pages/deletePlayer.jsp");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("error");
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

        if (player != null) {
            String value = request.getParameter("button");
            if ("Yes".equals(value)) {
                if (playerDAO.delete(player))
                    request.setAttribute("status", "Joueur supprimé");
                else
                    request.setAttribute("status", "Donnée invalide");

                dispatcher.dispatch(request, response, "/WEB-INF/pages/status.jsp");
            } else
                dispatcher.dispatch(request, response,"/WEB-INF/pages/player.jsp");
        } else {
            dispatcher.dispatch(request, response, "/WEB-INF/pages/accessDenied.jsp");
        }
    }
}
