package com.markgubatan.loltracker;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

/**
 * Data structure containing all the information for a single match.
 */
public class Match implements Parcelable {
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

    protected Match(Parcel in) {
        in.readList(redTeam, null);
        in.readList(blueTeam, null);
        timestamp = in.readLong();
        matchID = in.readLong();
        champion = in.readInt();
        queue = in.readString();
        winner = in.readString();
        duration = in.readInt();
        date = (Date) in.readSerializable();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(redTeam);
        dest.writeList(blueTeam);
        dest.writeLong(timestamp);
        dest.writeLong(matchID);
        dest.writeInt(champion);
        dest.writeString(queue);
        dest.writeString(winner);
        dest.writeInt(duration);
        dest.writeSerializable(date);
    }

    public static final Creator<Match> CREATOR = new Creator<Match>() {
        @Override
        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };

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
