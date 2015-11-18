package com.markgubatan.loltracker.tasks.json.parsing;

import com.markgubatan.loltracker.Match;
import com.markgubatan.loltracker.tasks.riot.ApiConstants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Parses more detailed information on a single match
 */
public class DetailedMatchParser {

    private ApiConstants api = new ApiConstants();
    private Match match;

    public DetailedMatchParser(Match match) {
        this.match = match;
    }

    public Match parse(JSONObject jsonMatch) throws JSONException {
        match.setDuration(jsonMatch.getInt(api.MATCH_ID));

        return match;
    }

}
