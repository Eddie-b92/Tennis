package com.tennis.controller.match;

import com.tennis.DAOFactory;
import com.tennis.Dispatcher;
import com.tennis.beans.Match;
import com.tennis.beans.Player;
import com.tennis.models.MatchDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "MatchServlet", value = "/match")
public class MatchServlet extends HttpServlet {

    private MatchDAO matchDAO;

    private Dispatcher dispatcher = new Dispatcher();

    // Gets us the connection from DAOFactory instance method when servlet gets initialized
    @Override
    public void init() throws ServletException {
        var daoFactory = DAOFactory.getInstance();
        this.matchDAO = daoFactory.getMatchDAO();
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
        dispatcher.dispatch(request, response, "/WEB-INF/pages/match.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login");
            return;
        }

        Match match = null;
        ArrayList<Match> matches = null;
        Player player;

        try {
            String firstName = request.getParameter("firstName");
            String lastName  = request.getParameter("lastName");
            String gender    = getGender   (request);
            String placement = getPlacement(request);

            if (firstName.length() > 20     ||
                lastName .length() > 20     ||
                gender   .length() > 1      || (
                !placement.equals("")       &&
                !placement.equals("winner") &&
                !placement.equals("finalist"))) {

                request.setAttribute("error", "Requête invalide!");
                dispatcher.dispatch(request, response, "/WEB-INF/pages/match.jsp");
            } else {
                player = new Player(firstName, lastName, gender);
                if (placement.equals("winner"))
                    matches = matchDAO.getWinnersMatches(player);
                else if (placement.equals("finalist"))
                    matches = matchDAO.getFinalistsMatches(player);
                else
                    matches = matchDAO.getMatches(player);

                request.setAttribute("matches", matches);
                if (matches != null)
                    dispatcher.dispatch(request, response, "/WEB-INF/pages/match.jsp");
                else
                    dispatcher.dispatch(request, response, "/WEB-INF/pages/error.jsp");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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

    private String getPlacement(HttpServletRequest request) {
        String[] placement = request.getParameterValues("placement");
        if (placement == null)    return "";
        if (placement.length > 1) return "";
        else
            switch (placement[0]) {
                case "winner"   -> {return "winner"  ;}
                case "finalist" -> {return "finalist";}
                default         -> {return ""        ;}
            }
    }
}
