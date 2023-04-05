package com.tennis;

import com.tennis.models.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOFactory {

    private String url, username, password;

    private DAOFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DAOFactory getInstance() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return new DAOFactory("jdbc:mysql://localhost:3306/tennis", "root", "");
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public PlayerDAO getPlayerDAO() {
        return new PlayerDAOImpl(this);
    }
    public TournamentDAO getTournamentDAO() {
        return new TournamentDAOImpl(this);
    }
    public MatchDAO getMatchDAO() {
        return new MatchDAOImpl(this);
    }
    public EventDAO getEventDAO() {
        return new EventDAOImpl(this);
    }
    public UserDAO getUserDAO() {
        return new UserDAOImpl(this);
    }
}
