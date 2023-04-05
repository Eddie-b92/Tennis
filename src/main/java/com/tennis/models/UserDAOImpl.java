package com.tennis.models;

import com.tennis.DAOFactory;
import com.tennis.beans.Tournament;
import com.tennis.beans.User;
import jakarta.servlet.http.HttpSession;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class UserDAOImpl implements UserDAO {
    private Connection connection               = null;
    private PreparedStatement preparedStatement = null;
    private Statement statement                 = null;
    private ResultSet resultSet                 = null;
    private DAOFactory daoFactory               = null;


    public UserDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public User login(User user) {
        User resultUser = null;
        String query = "SELECT * FROM connexion " +
                       "WHERE login = ?";

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, user.username());

            resultSet = preparedStatement.executeQuery();

            resultSet.next();
            resultUser = new User(resultSet.getInt   ("id"),
                                  resultSet.getString("login"),
                                  resultSet.getString("password"),
                                  resultSet.getInt   ("profil"));
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
        if (resultUser != null)
            if (hashPassword(user.password()).equals(resultUser.password()))
                return resultUser;
        return null;
    }

    @Override
    public boolean createUser(User user) {
        boolean flag = false;
        String query = "INSERT INTO connexion (login, password, profil) " +
                       "VALUES (?, ?, ?)";

        if (user.profile() != 1) {

            try {
                connection = daoFactory.getConnection();
                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, user.username());
                preparedStatement.setString(2, hashPassword(user.password()));
                preparedStatement.setInt   (3, user.profile());

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
                    if (preparedStatement != null) preparedStatement.close();
                    if (connection != null) connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    @Override
    public boolean createAdmin(User user) {
        boolean flag = false;
        String query = "INSERT INTO connexion (login, password, profil) " +
                       "VALUES (?, ?, ?)";

        try {
            connection = daoFactory.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, user.username());
            preparedStatement.setString(2, hashPassword(user.password()));
            preparedStatement.setInt   (3, 1);

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
    public boolean logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
            return true;
        } else return false;
    }




    private String hashPassword(String password) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        messageDigest.update(password.getBytes());

        byte[] hashArray = messageDigest.digest();

        StringBuilder stringBuilder = new StringBuilder();
        for (byte hash : hashArray)
            stringBuilder.append(Integer
                         .toString((hash & 0xff) + 0x100, 16)
                         .substring(1));

        return stringBuilder.toString();
    }


}
