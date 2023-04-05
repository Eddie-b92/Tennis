package com.tennis.controller.tournament;

import com.tennis.DAOFactory;
import com.tennis.Dispatcher;
import com.tennis.beans.Tournament;
import com.tennis.models.TournamentDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ModifyTournamentServlet", value = "/modifyTournament")
public class ModifyTournamentServlet extends HttpServlet {
    private TournamentDAO tournamentDAO;
    private Tournament tournament;
    private Dispatcher dispatcher = new Dispatcher();

    // Gets us the connection from DAOFactory instance method when servlet gets initialized
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
            int tournamentId = Integer.parseInt(request.getParameter("modify"));
            if (tournamentId > 0 && tournamentId < 100_000) {
                tournament = tournamentDAO.getTournament(tournamentId);

                request.setAttribute("name", tournament.name());
                request.setAttribute("code", tournament.code());

                dispatcher.dispatch(request, response, "/WEB-INF/pages/modifyTournament.jsp");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dispatcher.dispatch(request, response, "/WEB-INF/pages/error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login");
            return;
        }  else if ((int)session.getAttribute("permission") != 1) {
            response.sendRedirect("player");
            return;
        }

        try {
            String name = request.getParameter("name");
            String code = request.getParameter("code") ;

            if (name.equals("")    ||
                code .equals("")   ||
                name.length() > 20 ||
                code.length() != 2 ) {
                dispatcher.dispatch(request, response, "/WEB-INF/pages/error.jsp");
                return;
            }
            request.setAttribute("name", name);
            request.setAttribute("code", code);

            if (tournament != null) {
                // Give tournament the updated params
                tournament.setName(name);
                tournament.setCode(code.toUpperCase());

                if (tournamentDAO.update(tournament)) {
                    // Update table
                    request.setAttribute("tournaments", tournamentDAO.search(tournament));
                    // Prevent user from accessing the cashed page with back button
                    response.setHeader("Cache-Control", "no-cache, no-store");
                    response.setDateHeader("Expires", 0);
                    response.setHeader("Pragma", "no-cache");

                    dispatcher.dispatch(request, response, "/WEB-INF/pages/tournament.jsp");
                } else {
                    request.setAttribute("error", "Tournoi n'existe pas");
                    dispatcher.dispatch(request, response, "/WEB-INF/pages/tournament.jsp");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            dispatcher.dispatch(request, response, "/WEB-INF/pages/error.jsp");
        }
    }
}
