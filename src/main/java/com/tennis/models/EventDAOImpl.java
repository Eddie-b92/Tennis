package com.tennis.models;

import com.tennis.DAOFactory;
import com.tennis.beans.Event;
import com.tennis.beans.Player;
import com.tennis.beans.Tournament;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class EventDAOImpl implements EventDAO {

    private final DAOFactory daoFactory;
    private Connection connection               = null;
    private Statement statement                 = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet                 = null;
    private ArrayList<Event> events;

    public EventDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public ArrayList<Event> getAll() {
        String query = "SELECT joueur.nom AS lastName, joueur.prenom AS firstName, IF (joueur.ID = match_tennis.ID_VAINQUEUR, 'vainqueur', 'finaliste') AS placement, tournoi.nom AS tournament " +
                       "FROM joueur " +
                       "INNER JOIN match_tennis ON joueur.id = match_tennis.id_vainqueur OR joueur.id = match_tennis.id_finaliste " +
                       "INNER JOIN epreuve ON match_tennis.id_epreuve = epreuve.id " +
                       "INNER JOIN tournoi ON epreuve.ID_TOURNOI = tournoi.ID";

        events = null;
        try {
            connection = daoFactory.getConnection();
            statement  = connection.createStatement();

            resultSet = statement.executeQuery(query);
            events = new ArrayList<>();
            Event event;

            while (resultSet.next()) {
                event = new Event(new Player(resultSet.getString("firstName"),
                                             resultSet.getString("lastName")),
                                             resultSet.getString("placement"),
                              new Tournament(resultSet.getString("tournament")));
                events.add(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement  != null) statement .close();
                if (resultSet  != null) resultSet .close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return events;
    }

    @Override
    public ArrayList<Event> getEventsByYear(int year) {
        String query = "SELECT joueur.nom AS lastName, joueur.prenom AS firstName, IF (joueur.ID = match_tennis.ID_VAINQUEUR, 'vainqueur', 'finaliste') AS placement, tournoi.nom AS tournament " +
                       "FROM joueur " +
                       "INNER JOIN match_tennis ON joueur.id = match_tennis.id_vainqueur OR joueur.id = match_tennis.id_finaliste " +
                       "INNER JOIN epreuve ON match_tennis.id_epreuve = epreuve.id " +
                       "INNER JOIN tournoi ON epreuve.ID_TOURNOI = tournoi.ID " +
                       "WHERE epreuve.annee = ?";

        events = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, year);


            resultSet = preparedStatement.executeQuery();
            events = new ArrayList<>();
            Event event;

            while (resultSet.next()) {
                event = new Event(new Player(resultSet.getString("firstName"),
                                             resultSet.getString("lastName")),
                                             resultSet.getString("placement"),
                              new Tournament(resultSet.getString("tournament")));
                events.add(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (resultSet         != null) resultSet        .close();
                if (connection        != null) connection       .close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return events;

    }

    @Override
    public ArrayList<Event> getEventsByGender(String gender) {
        String query = "SELECT joueur.nom AS lastName, joueur.prenom AS firstName, IF (joueur.ID = match_tennis.ID_VAINQUEUR, 'vainqueur', 'finaliste') AS placement, tournoi.nom AS tournament " +
                       "FROM joueur " +
                       "INNER JOIN match_tennis ON joueur.id = match_tennis.id_vainqueur OR joueur.id = match_tennis.id_finaliste " +
                       "INNER JOIN epreuve ON match_tennis.id_epreuve = epreuve.id " +
                       "INNER JOIN tournoi ON epreuve.ID_TOURNOI = tournoi.ID " +
                       "WHERE joueur.sexe = ?";

        events = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, gender);


            resultSet = preparedStatement.executeQuery();
            events = new ArrayList<>();
            Event event;

            while (resultSet.next()) {
                event = new Event(new Player(resultSet.getString("firstName"),
                                             resultSet.getString("lastName")),
                                             resultSet.getString("placement"),
                              new Tournament(resultSet.getString("tournament")));
                events.add(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (resultSet         != null) resultSet        .close();
                if (connection        != null) connection       .close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return events;
    }

    @Override
    public ArrayList<Event> search(int year, String gender) {
        String query = "SELECT joueur.nom AS lastName, joueur.prenom AS firstName, IF (joueur.ID = match_tennis.ID_VAINQUEUR, 'vainqueur', 'finaliste') AS placement, tournoi.nom AS tournament " +
                       "FROM joueur " +
                       "INNER JOIN match_tennis ON joueur.id = match_tennis.id_vainqueur OR joueur.id = match_tennis.id_finaliste " +
                       "INNER JOIN epreuve ON match_tennis.id_epreuve = epreuve.id " +
                       "INNER JOIN tournoi ON epreuve.ID_TOURNOI = tournoi.ID " +
                       "WHERE joueur.sexe = ? " +
                       "AND epreuve.annee = ?";

        events = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, gender);
            preparedStatement.setInt   (2, year);

            resultSet = preparedStatement.executeQuery();
            events = new ArrayList<>();
            Event event;

            while (resultSet.next()) {
                event = new Event(new Player(resultSet.getString("firstName"),
                                             resultSet.getString("lastName")),
                                             resultSet.getString("placement"),
                              new Tournament(resultSet.getString("tournament")));
                events.add(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (resultSet         != null) resultSet        .close();
                if (connection        != null) connection       .close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return events;
    }
}
