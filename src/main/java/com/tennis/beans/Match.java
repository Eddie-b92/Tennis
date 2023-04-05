package com.tennis.beans;

public class Match {
    private int id;
    private Player player;
    private int nbrMatches;

    public Match(int id, Player player, int nbrMatches) {
        this.id = id;
        this.player = player;
        this.nbrMatches = nbrMatches;
    }

    public Match(Player player, int nbrMatches) {
        this.player = player;
        this.nbrMatches = nbrMatches;
    }

    public Player player() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int nbrMatches() {
        return nbrMatches;
    }

    public void setNbrMatches(int nbrMatches) {
        this.nbrMatches = nbrMatches;
    }

    public int id() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
