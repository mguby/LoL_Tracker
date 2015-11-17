package com.markgubatan.loltracker.tasks.json.parsing;

import com.markgubatan.loltracker.Match;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses the basic information of a match given from the RiotAPI MatchList call.
 */
public class BasicMatchParser {
    private static final String MATCHID = "matchId";
    private static final String TIMESTAMP = "timestamp";
    private static final String CHAMPION = "champion";
    private static final String QUEUE = "queue";
    private int numToParse;

    public BasicMatchParser(int numToParse) {
        this.numToParse = numToParse;
    }

    public List<Match> parseMatches(JSONArray jsonMatches) throws JSONException {
        List<Match> matches = new ArrayList<>();

        for(int i = 0; i < numToParse; i++) {
            Match match = parse(jsonMatches.getJSONObject(i));
            matches.add(match);
        }
        return matches;
    }


    public Match parse(JSONObject match) throws JSONException {
        long matchID = match.getLong(MATCHID);
        long timestamp = match.getLong(TIMESTAMP);
        int champion = match.getInt(CHAMPION);
        String queue = match.getString(QUEUE);

        return new Match(timestamp, champion, matchID, queue);
    }
}
