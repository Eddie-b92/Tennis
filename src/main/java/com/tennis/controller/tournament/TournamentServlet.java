package com.tennis.controller.tournament;
import com.tennis.DAOFactory;
import com.tennis.Dispatcher;
import com.tennis.beans.Tournament;
import com.tennis.models.TournamentDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "TournamentServlet", value = "/tournament")
public class TournamentServlet extends HttpServlet {
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
        }
        dispatcher.dispatch(request, response, "/WEB-INF/pages/tournament.jsp");
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


        tournament = null;
        String value = request.getParameter("button");
        try {
            int permission = (int)(session.getAttribute("permission"));
            String name = request.getParameter("name");
            String code = request.getParameter("code");

            if (name.length() > 20 || code.length() > 2) {
                request.setAttribute("error", "Requête invalide!");
                dispatcher.dispatch(request, response, "/WEB-INF/pages/tournament.jsp");
            }

            else if ("add".equals(value) && permission == 1) {

                if (name.equals("") || code.length() != 2) {
                    request.setAttribute("error", "Requête invalide!");
                    dispatcher.dispatch(request, response, "/WEB-INF/pages/tournament.jsp");
                } else
                    add(request, response, name, code.toUpperCase());

            } else if ("search".equals(value))
                search(request, response, name, code);

            else dispatcher.dispatch(request, response, "/WEB-INF/pages/error.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            dispatcher.dispatch(request, response, "/WEB-INF/pages/error.jsp");
        }
    }

    private void add(HttpServletRequest  request,
                     HttpServletResponse response,
                     String              name,
                     String              code)
            throws Exception {

        tournament = new Tournament(name, code);

        request.setAttribute("tournamentAdded", tournamentDAO.add(tournament));
        // Refresh table
        request.setAttribute("tournaments",     tournamentDAO.search(tournament));

        dispatcher.dispatch(request, response, "/WEB-INF/pages/tournament.jsp");
    }

    private void search(HttpServletRequest  request,
                        HttpServletResponse response,
                        String              name,
                        String              code)
            throws Exception {
        tournament = new Tournament(name, code);

        request.setAttribute("tournaments", tournamentDAO.search(tournament));

        dispatcher.dispatch(request, response, "/WEB-INF/pages/tournament.jsp");
    }
}
