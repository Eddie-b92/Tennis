package com.tennis.controller.tournament;

import com.tennis.DAOFactory;
import com.tennis.Dispatcher;
import com.tennis.beans.Player;
import com.tennis.beans.Tournament;
import com.tennis.models.PlayerDAO;
import com.tennis.models.TournamentDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "DeleteTournamentServlet", value = "/deleteTournament")
public class DeleteTournamentServlet extends HttpServlet {

    private TournamentDAO tournamentDAO;
    private Tournament tournament;
    private Dispatcher dispatcher = new Dispatcher();

    @Override
    public void init() throws ServletException {
        var daoFactory = DAOFactory.getInstance();
        this.tournamentDAO = daoFactory.getTournamentDAO();
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
            response.sendRedirect("tournament");
            return;
        }


        tournament = null;
        try {
            int tournamentId = Integer.parseInt(request.getParameter("delete"));
            if (tournamentId > 0 && tournamentId < 100_000) {
                tournament = tournamentDAO.getTournament(tournamentId);

                if (tournament != null)
                    request.setAttribute("name", tournament.name());

                dispatcher.dispatch(request, response, "/WEB-INF/pages/deleteTournament.jsp");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("/WEB-INF/pages/error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null
        || (int)session.getAttribute("permission") != 1) {
            response.sendRedirect("login");
            return;
        }

        if (tournament != null) {
            String value = request.getParameter("button");
            if ("Yes".equals(value)) {
                if (tournamentDAO.delete(tournament)) {
                    // Prevent user from accessing the cashed page with back button
                    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                    response.setHeader("Pragma", "no-cache");
                    response.setDateHeader("Expires", 0);

                    request.setAttribute("status", "Tournoi supprimé");
                }
                else
                    request.setAttribute("status", "Donnée invalide");

                dispatcher.dispatch(request, response, "/WEB-INF/pages/status.jsp");
            } else
                dispatcher.dispatch(request, response,"/WEB-INF/pages/tournament.jsp");
        } else {
            dispatcher.dispatch(request, response, "/WEB-INF/pages/accessDenied.jsp");
        }
    }
}
