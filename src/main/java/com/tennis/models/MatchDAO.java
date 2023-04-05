package com.tennis.models;

import com.tennis.beans.Match;
import com.tennis.beans.Player;

import java.util.ArrayList;

public interface MatchDAO {
    public ArrayList<Match> getAll();
    public ArrayList<Match> getMatches         (Player player);
    public ArrayList<Match> getWinnersMatches  (Player player);
    public ArrayList<Match> getFinalistsMatches(Player player);
}
