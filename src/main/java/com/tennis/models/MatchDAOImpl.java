package com.tennis.models;

import com.tennis.DAOFactory;
import com.tennis.beans.Match;
import com.tennis.beans.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MatchDAOImpl implements MatchDAO {
    private Connection connection               = null;
    private PreparedStatement preparedStatement = null;
    private Statement statement                 = null;
    private ResultSet resultSet                 = null;
    private final DAOFactory daoFactory;

    public MatchDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public ArrayList<Match> getAll() {
        return null;
    }

    @Override
    public ArrayList<Match> getMatches(Player player) {

        String query = "SELECT match_tennis.id as id, nom, prenom, sexe, COUNT(match_tennis.id) AS nbrMatch FROM joueur " +
                       "INNER JOIN match_tennis ON joueur.ID = match_tennis.ID_VAINQUEUR " +
                                               "OR joueur.ID = match_tennis.ID_FINALISTE " +
                       "WHERE LOWER(joueur.nom) LIKE ? " +
                       "AND LOWER(joueur.prenom) LIKE ? ";

        if (!player.gender().equals(""))
            query += "AND joueur.sexe = ? ";

        query += "GROUP BY joueur.nom " +
                 "ORDER BY nbrMatch DESC";

        return matches(player, query);
    }

    @Override
    public ArrayList<Match> getWinnersMatches(Player player) {


        String query = "SELECT match_tennis.id as id, nom, prenom, sexe, COUNT(match_tennis.id) AS nbrMatch FROM joueur " +
                       "INNER JOIN match_tennis ON joueur.ID = match_tennis.ID_VAINQUEUR " +
                       "WHERE LOWER(joueur.nom) LIKE ? " +
                       "AND LOWER(joueur.prenom) LIKE ? ";

        if (!player.gender().equals(""))
            query += "AND joueur.sexe = ?";
        query += "GROUP BY joueur.nom " +
                 "ORDER BY nbrMatch DESC";

        return matches(player, query);
    }

    @Override
    public ArrayList<Match> getFinalistsMatches(Player player) {
        String query = "SELECT match_tennis.id as id, nom, prenom, sexe, COUNT(match_tennis.id) AS nbrMatch FROM joueur " +
                       "INNER JOIN match_tennis ON joueur.ID = match_tennis.ID_FINALISTE " +
                       "WHERE LOWER(joueur.nom) LIKE ? " +
                       "AND LOWER(joueur.prenom) LIKE ? ";

        if (!player.gender().equals(""))
            query += "AND joueur.sexe = ?";
        query += "GROUP BY joueur.nom " +
                 "ORDER BY nbrMatch DESC";

        return matches(player, query);
    }

    private ArrayList<Match> matches(Player player, String query) {
        ArrayList<Match> matches = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, "%"+ player.lastName().toLowerCase() +"%");
            preparedStatement.setString(2, "%"+ player.firstName().toLowerCase() +"%");

            if (!player.gender().equals(""))
                preparedStatement.setString(3, player.gender());

            resultSet = preparedStatement.executeQuery();
            matches = new ArrayList<>();
            Match match;

            while (resultSet.next()) {
                player = new Player(resultSet.getString("prenom"),
                                    resultSet.getString("nom"),
                                    resultSet.getString("sexe"));
                match = new Match (resultSet.getInt("id"),
                                   player,
                                   resultSet.getInt("nbrMatch"));
                matches.add(match);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement  != null) preparedStatement.close();
                if (resultSet          != null) resultSet        .close();
                if (connection         != null) connection       .close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return matches;
    }


}
