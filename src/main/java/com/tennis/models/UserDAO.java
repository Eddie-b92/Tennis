package com.tennis.models;

import com.tennis.beans.User;
import jakarta.servlet.http.HttpSession;

public interface UserDAO {
    User login      (User user);
    boolean createUser (User user);
    boolean createAdmin(User user);
    boolean logout     (HttpSession session);                           //(String username, String password);
}
