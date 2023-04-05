package com.tennis.controller.event;

import com.tennis.DAOFactory;
import com.tennis.Dispatcher;
import com.tennis.models.EventDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "EventServlet", value = "/event")
public class EventServlet extends HttpServlet {

    private EventDAO eventDAO;

    private Dispatcher dispatcher = new Dispatcher();

    // Gets us the connection from DAOFactory instance method when servlet gets initialized
    @Override
    public void init() throws ServletException {
        var daoFactory = DAOFactory.getInstance();
        this.eventDAO = daoFactory.getEventDAO();
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
        dispatcher.dispatch(request, response, "/WEB-INF/pages/event.jsp");
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
            String yearStr = request.getParameter("year");
            String gender = getGender(request);
            int year = 0;

            if (yearStr.length() != 4 && !yearStr.equals("") && !isInt(yearStr)) {
                request.setAttribute("error", "INNNNNNVALIIIID year");
                dispatcher.dispatch(request, response, "/WEB-INF/pages/event.jsp");

            } else if (gender.equals("") && yearStr.equals("")) {
                request.setAttribute("events", eventDAO.getAll());
                dispatcher.dispatch(request, response, "/WEB-INF/pages/event.jsp");

            } else if (!gender.equals("") && yearStr.equals("")) {
                request.setAttribute("events", eventDAO.getEventsByGender(gender));
                dispatcher.dispatch(request, response, "/WEB-INF/pages/event.jsp");

            } else if (gender.equals("") && !yearStr.equals("")) {
                year = Integer.parseInt(yearStr);
                request.setAttribute("events", eventDAO.getEventsByYear(year));
                dispatcher.dispatch(request, response, "/WEB-INF/pages/event.jsp");

            } else if (!gender.equals("") && !yearStr.equals("")) {
                year = Integer.parseInt(yearStr);
                request.setAttribute("events", eventDAO.search(year, gender));
                dispatcher.dispatch(request, response, "/WEB-INF/pages/event.jsp");

            } else {
                request.setAttribute("error", "Requête ");
                dispatcher.dispatch(request, response, "/WEB-INF/pages/event.jsp");
            }
        } catch (Exception e) {
            System.out.println("Je suis dans le catch :" + e.getMessage());
            dispatcher.dispatch(request, response, "/WEB-INF/pages/error.jsp");
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
    private boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

}
