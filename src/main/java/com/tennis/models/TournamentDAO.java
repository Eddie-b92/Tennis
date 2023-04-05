package com.tennis.models;

import com.tennis.beans.Player;
import com.tennis.beans.Tournament;

import java.util.List;

public interface TournamentDAO {
    public List<Tournament> getAll();

    public List<Tournament> search(Tournament tournament);

    public Tournament getTournament(int id);

    public boolean add   (Tournament tournament);

    public boolean update(Tournament tournament);

    public boolean delete(Tournament tournament);
}
