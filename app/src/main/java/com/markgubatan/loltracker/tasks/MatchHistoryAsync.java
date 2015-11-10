package com.markgubatan.loltracker.tasks;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.markgubatan.loltracker.Match;
import com.markgubatan.loltracker.R;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Asynchronous task to retrieve a player's match history using the Riot Games API.
 * Riot's API requires a player's ID rather than a player's name to retrieve their
 * match history and thus we first have to convert our summoner's name into an ID from
 * our String Array resource.
 */
public class MatchHistoryAsync extends AsyncTask<String, Void, List<Match>> {

    private static final String MATCHES = "matches";
    private static final String MATCHID = "matchId";
    private static final String TIMESTAMP = "timestamp";
    private static final String CHAMPION = "champion";
    private static final String TAG = "MatchHistoryAsync";
    private static final int RATE_LIMIT_EXCEEDED = 429;

    private String player, team;
    private Context context;
    private Resources res;

    public MatchHistoryAsync(String team, String player, Context context) {
        this.player = player;
        this.context = context;
        this.team = team;
        res = context.getResources();
    }

    @Override
    protected List<Match> doInBackground(String... params) {
        List<Match> matches = new ArrayList<>();
        try {
            URL url = constructQuery();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if(responseCode == RATE_LIMIT_EXCEEDED) {
                Log.e(TAG, "Rate Limit Exceeded");
                return null;
            }
            InputStream in = new BufferedInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"), 10000);
            StringBuilder builder = new StringBuilder();
            String line = null;

            while((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }

            String jsonString = builder.toString();
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray retrievedMatches = jsonObject.getJSONArray(MATCHES);

            // We only retrieve the most recent 5 retrievedMatches because time/space
            for(int i = 0; i < 10; i++) {
                JSONObject match = retrievedMatches.getJSONObject(i);
                int matchID = match.getInt(MATCHID);
                long timestamp = match.getLong(TIMESTAMP);
                int champion = match.getInt(CHAMPION);

                Match curMatch = new Match(timestamp, champion, matchID);
                matches.add(curMatch);
            }
        }
        catch(MalformedURLException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        catch(JSONException e) {
            e.printStackTrace();
        }

        return matches;
    }

    /**
     * Constructs query to be used in the API request.
     * @return      HTTP query
     */
    private URL constructQuery() throws MalformedURLException {
        String summonerID = getSummonerIDFromName();
        String apiKey = res.getString(R.string.riot_api_key);
        return new URL("https://https://na.api.pvp.net/api/lol/na/v2.2/matchlist/by-summoner/"
                + summonerID + "?api_key=" + apiKey);
    }

    /**
     * Riots API requires a summoner ID rather than a summoner name in order to retrieve
     * matches so thus we have to convert our summoner's name that we clicked on into an
     * ID.
     * @return          the summoner ID for the current player
     */
    private String getSummonerIDFromName() {
        Resources res = context.getResources();
        String teamTrimmed = team.replace(' ', '_').toLowerCase();
        int teamArrayID = res.getIdentifier(teamTrimmed, "array", context.getPackageName());
        String[] players = res.getStringArray(teamArrayID);

        String teamIDs = teamTrimmed + "_ids";
        int idArray = res.getIdentifier(teamIDs, "array", context.getPackageName());
        String[] ids = res.getStringArray(idArray);
        int position = getPlayerPosition(players, player);

        return ids[position];
    }

    /**
     * Our String array resource of player ids is sorted by team and by position so
     * we need to get the player's position in the player name id to know where to search
     * in the ids array.
     * @param player    the player being searched for
     * @param players   the list of players the current player is within
     * @return          the index position of the current player in the players array
     */
    private int getPlayerPosition(String[] players, String player) {
        for(int i = 0; i < players.length; i++) {
            if(players[i].equals(player))
                return i;
        }
        return 0;
    }

    @Override
    protected void onPostExecute(List<Match> matchIDs) {

    }
}
