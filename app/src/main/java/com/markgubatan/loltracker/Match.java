package com.markgubatan.loltracker;

import java.util.Date;
import java.util.List;

/**
 * Data structure containing all the information for a single match.
 */
public class Match {
    private List<Player> blueTeam;
    private List<Player> redTeam;
    private long timestamp;
    private int champion;
    private long matchID;
    private Date date;
    private String queue;
    private String winner;

    private int duration;

    public Match(long timestamp, int champion, long matchID, String queue) {
        this.timestamp = timestamp;
        this.champion = champion;
        this.matchID = matchID;
        this.queue = queue;
        date = new Date(timestamp);
    }

    public List<Player> getBlueTeam() {
        return blueTeam;
    }

    public void setBlueTeam(List<Player> blueTeam) {
        this.blueTeam = blueTeam;
    }

    public List<Player> getRedTeam() {
        return redTeam;
    }

    public void setRedTeam(List<Player> redTeam) {
        this.redTeam = redTeam;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getDate() {
        return date.toString();
    }

    public String getQueue() {
        return queue;
    }

    public int getChampion() {
        return champion;
    }

    public long getMatchID() {
        return matchID;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
