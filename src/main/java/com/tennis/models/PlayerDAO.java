package com.tennis.models;

import com.tennis.beans.Player;

import java.util.List;

public interface PlayerDAO {
    public List<Player> getAll();

    public List<Player> search(Player player);

    public Player getPlayer(int id);

    public boolean add(Player player);

    public boolean update(Player player);

    public boolean delete(Player player);
}
