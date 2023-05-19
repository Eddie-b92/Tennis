package com.tennis.models;

import com.tennis.DAOFactory;
import com.tennis.beans.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAOImpl implements PlayerDAO {
    private Connection connection               = null;
    private PreparedStatement preparedStatement = null;
    private Statement statement                 = null;
    private ResultSet resultSet                 = null;
    private DAOFactory daoFactory               = null;

    public PlayerDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public List<Player> getAll() {
        List<Player> players = null;

        String query = "SELECT * FROM joueur " +
                       "ORDER BY joueur.nom";

        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery(query);
            players = new ArrayList<>();
            Player resultPlayer;

            while (resultSet.next()) {
                resultPlayer = new Player(resultSet.getInt("id"),
                                          resultSet.getString("prenom"),
                                          resultSet.getString("nom"),
                                          resultSet.getString("sexe"));
                players.add(resultPlayer);
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
        return players;
    }

    @Override
    public List<Player> search(Player player) {

        List<Player> players = null;

        String query = "SELECT * FROM joueur " +
                       "WHERE LOWER(nom)    LIKE ? " +
                       "AND   LOWER(prenom) LIKE ? ";

        if (!player.gender().equals(""))
            query += " AND sexe = ?";

        query += " ORDER BY joueur.nom";

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, "%"+ player.lastName().toLowerCase() +"%");
            preparedStatement.setString(2, "%"+ player.firstName().toLowerCase() +"%");

            if (!player.gender().equals(""))
                preparedStatement.setString(3, player.gender());

            resultSet = preparedStatement.executeQuery();
            players = new ArrayList<>();
            Player resultPlayer;

            while (resultSet.next()) {
                resultPlayer = new Player(resultSet.getInt("id"),
                                          resultSet.getString("prenom"),
                                          resultSet.getString("nom"),
                                          resultSet.getString("sexe"));
                players.add(resultPlayer);
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
        return players;
    }

    @Override
    public Player getPlayerById(int id) {
        Player player = null;

        String query = "SELECT * FROM joueur " +
                       "WHERE id = ? ";

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            player = new Player(resultSet.getInt("id"),
                                resultSet.getString("prenom"),
                                resultSet.getString("nom"),
                                resultSet.getString("sexe"));
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
        return player;
    }

    @Override
    public boolean add(Player player) {

        boolean flag = false;
        String query = "INSERT INTO joueur (nom, prenom, sexe) " +
                       "VALUES (?, ?, ?)";

        try {
            connection = daoFactory.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, player.lastName() );
            preparedStatement.setString(2, player.firstName());
            preparedStatement.setString(3, player.gender()   );

            preparedStatement.executeUpdate();
            connection.commit();
            flag = true;

        } catch (Exception e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    System.err.println("Add query is being rolled back");
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } finally {
            try {
                if (preparedStatement  != null) preparedStatement.close();
                if (connection         != null) connection       .close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }


    @Override
    public boolean update(Player player) {
        boolean flag = false;
        String query = "UPDATE joueur " +
                       "SET joueur.nom = ?, joueur.prenom = ?, joueur.sexe = ? " +
                       "WHERE id = ?";

        try {
            connection = daoFactory.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, player.lastName() );
            preparedStatement.setString(2, player.firstName());
            preparedStatement.setString(3, player.gender()   );
            preparedStatement.setInt   (4, player.id()       );

            preparedStatement.executeUpdate();
            connection.commit();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    System.err.println("Update is being rolled back");
                    connection.rollback();
                } catch (SQLException ex) {
                    e.printStackTrace();
                }
            }
        } finally {
            try {
                if (preparedStatement  != null) preparedStatement.close();
                if (connection         != null) connection       .close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    @Override
    public boolean delete(Player player) {
        boolean flag = false;
        String foreignKeyCheckOff   = "SET FOREIGN_KEY_CHECKS=0;";

        String query                = "DELETE joueur.*, match_tennis.*, score_vainqueur.* FROM joueur " +
                                      "INNER JOIN match_tennis ON joueur.ID = match_tennis.ID_VAINQUEUR " +
                                            "OR joueur.ID = match_tennis.ID_FINALISTE " +
                                      "LEFT JOIN score_vainqueur ON match_tennis.ID = score_vainqueur.ID_MATCH " +
                                      "WHERE joueur.ID = ? ";

        String foreignKeyCheckOn    = "SET FOREIGN_KEY_CHECKS=1;";

        String conditionCheckQuery  = "SELECT COUNT(*) as number FROM joueur " +
                                      "INNER JOIN match_tennis ON joueur.ID = match_tennis.ID_VAINQUEUR " +
                                            "OR joueur.ID = match_tennis.ID_FINALISTE " +
                                      "LEFT JOIN score_vainqueur ON match_tennis.ID = score_vainqueur.ID_MATCH " +
                                      "WHERE joueur.ID = ?";

        String simpleQuery          = "DELETE FROM joueur " +
                                      "WHERE joueur.id = ?";

        try {
            connection = daoFactory.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(conditionCheckQuery);
            preparedStatement.setInt(1, player.id());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            // Check if result has a foreign key constraint
            if (resultSet.getInt("number") > 0) {
                // first statement to turn OFF the foreign key constraint
                statement = connection.createStatement();
                statement.execute(foreignKeyCheckOff);
                statement.close();

                // delete query
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, player.id());
                preparedStatement.executeUpdate();

                // second statement to turn ON the foreign key constraint
                statement = connection.createStatement();
                statement.execute(foreignKeyCheckOn);
                statement.close();

            } else {
                preparedStatement = connection.prepareStatement(simpleQuery);
                preparedStatement.setInt(1, player.id());
                preparedStatement.executeUpdate();
            }
            connection.commit();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    System.err.println("Delete is being rolled back");
                    connection.rollback();
                } catch (SQLException ex) {
                    e.printStackTrace();
                }
            }
        } finally {
            try {
                if (preparedStatement  != null) preparedStatement.close();
                if (connection         != null) connection       .close();
                if (resultSet          != null) resultSet        .close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
}
