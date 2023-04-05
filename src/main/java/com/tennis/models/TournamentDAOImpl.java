package com.tennis.models;

import com.tennis.DAOFactory;
import com.tennis.beans.Player;
import com.tennis.beans.Tournament;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TournamentDAOImpl implements TournamentDAO {

    private Connection connection               = null;
    private PreparedStatement preparedStatement = null;
    private Statement statement                 = null;
    private ResultSet resultSet                 = null;
    private DAOFactory daoFactory               = null;

    public TournamentDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public List<Tournament> getAll() {

        List<Tournament> tournaments = null;

        String query = "SELECT * FROM tournoi " +
                       "ORDER BY tournoi.nom";

        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery(query);
            tournaments = new ArrayList<>();
            Tournament resultTournament;

            while (resultSet.next()) {
                resultTournament = new Tournament(resultSet.getInt("id"),
                                                  resultSet.getString("nom"),
                                                  resultSet.getString("code"));
                tournaments.add(resultTournament);
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
        return tournaments;
    }

    @Override
    public List<Tournament> search(Tournament tournament) {
        List<Tournament> tournaments = null;

        String query = "SELECT * FROM tournoi " +
                       "WHERE LOWER(nom)  LIKE ? " +
                       "AND   LOWER(code) LIKE ? " +
                       "ORDER BY tournoi.nom";

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, "%"+ tournament.name().toLowerCase() +"%");
            preparedStatement.setString(2, "%"+ tournament.code().toLowerCase() +"%");

            resultSet = preparedStatement.executeQuery();
            tournaments = new ArrayList<>();
            Tournament resultTournament;

            while (resultSet.next()) {
                resultTournament = new Tournament(resultSet.getInt   ("id"),
                                                  resultSet.getString("nom"),
                                                  resultSet.getString("code"));
                tournaments.add(resultTournament);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet         != null) resultSet        .close();
                if (connection        != null) connection       .close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tournaments;
    }

    @Override
    public Tournament getTournament(int id) {
        Tournament tournament = null;

        String query = "SELECT * FROM tournoi " +
                       "WHERE id = ?";

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            resultSet.next();
                tournament = new Tournament(resultSet.getInt   ("id"),
                                            resultSet.getString("nom"),
                                            resultSet.getString("code"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet         != null) resultSet        .close();
                if (connection        != null) connection       .close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tournament;
    }

    @Override
    public boolean add(Tournament tournament) {
        boolean flag = false;
        String query = "INSERT INTO tournoi (nom, code) " +
                       "VALUES (?, ?)";

        try {
            connection = daoFactory.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, tournament.name() );
            preparedStatement.setString(2, tournament.code());

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
    public boolean update(Tournament tournament) {
        boolean flag = false;
        String query = "UPDATE tournoi " +
                       "SET tournoi.nom = ?, tournoi.code = ? " +
                       "WHERE id = ?";

        try {
            connection = daoFactory.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, tournament.name());
            preparedStatement.setString(2, tournament.code());
            preparedStatement.setInt   (3, tournament.id()  );

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
    public boolean delete(Tournament tournament) {
        boolean flag = false;

        String foreignKeyCheckOff  = "SET FOREIGN_KEY_CHECKS=0;";

        String query               = "DELETE tournoi, epreuve, match_tennis, score_vainqueur FROM tournoi " +
                                     "INNER JOIN epreuve ON tournoi.ID = epreuve.ID_TOURNOI " +
                                     "INNER JOIN match_tennis ON epreuve.ID = match_tennis.ID_EPREUVE " +
                                     "INNER JOIN score_vainqueur ON match_tennis.ID = score_vainqueur.ID_MATCH " +
                                     "WHERE tournoi.ID = ?";

        String foreignKeyCheckOn   = "SET FOREIGN_KEY_CHECKS=1;";

        String conditionCheckQuery = "SELECT COUNT(*) as number FROM tournoi " +
                                     "INNER JOIN epreuve ON tournoi.ID = epreuve.ID_TOURNOI " +
                                     "INNER JOIN match_tennis ON epreuve.ID = match_tennis.ID_EPREUVE " +
                                     "INNER JOIN score_vainqueur ON match_tennis.ID = score_vainqueur.ID_MATCH " +
                                     "WHERE tournoi.ID = ?";

        String simpleQuery         = "DELETE FROM tournoi " +
                                     "WHERE tournoi.id = ?";

        try {
            connection = daoFactory.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(conditionCheckQuery);
            preparedStatement.setInt(1, tournament.id());
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
                preparedStatement.setInt(1, tournament.id());
                preparedStatement.executeUpdate();

                // second statement to turn ON the foreign key constraint
                statement = connection.createStatement();
                statement.execute(foreignKeyCheckOn);
                statement.close();
            } else {
                preparedStatement = connection.prepareStatement(simpleQuery);
                preparedStatement.setInt(1, tournament.id());
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
