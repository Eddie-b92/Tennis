package com.tennis;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Dispatcher {
    public void dispatch(HttpServletRequest request, HttpServletResponse response, String url)
            throws ServletException, IOException {
        var requestDispatcher = request.getRequestDispatcher(url);
        requestDispatcher.forward(request, response);
    }
}
