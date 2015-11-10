package com.markgubatan.loltracker;

import java.util.Date;
import java.util.List;

/**
 * Data structure containing all the information for a single match.
 */
public class Match {
    private List<String> players;
    private long timestamp;
    private int champion;
    private long matchID;
    private Date date;

    public Match(long timestamp, int champion, long matchID) {
        this.timestamp = timestamp;
        this.champion = champion;
        this.matchID = matchID;
        date = new Date(timestamp);
    }

    public List<String> getPlayers() {
        return players;
    }

    public String getDate() {
        return date.toString();
    }

    public int getChampion() {
        return champion;
    }

    public long getMatchID() {
        return matchID;
    }


}
