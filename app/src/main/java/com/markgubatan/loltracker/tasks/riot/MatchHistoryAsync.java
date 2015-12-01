package com.markgubatan.loltracker.tasks.riot;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.markgubatan.loltracker.Match;
import com.markgubatan.loltracker.R;
import com.markgubatan.loltracker.listeners.OnMatchHistoryCompleteListener;
import com.markgubatan.loltracker.tasks.http.HTTPGetter;
import com.markgubatan.loltracker.tasks.http.StreamToStringConverter;
import com.markgubatan.loltracker.tasks.json.parsing.BasicMatchParser;
import com.markgubatan.loltracker.tasks.json.retrieval.JSONRetriever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Asynchronous task to retrieve the basic information for a player's match history using the
 * Riot Games API. Riot's API requires a player's ID rather than a player's name to retrieve their
 * match history and thus we first have to convert our summoner's name into an ID from
 * our String Array resource. Also, since this API call only gives us the timestamp, champion, and
 * matchID, we need another API call to get more detailed information on the matches.
 */
public class MatchHistoryAsync extends AsyncTask<String, Void, List<Match>> {
    private static final String MATCHES = "matches";

    private static final String TAG = "MatchHistoryAsync";
    private static final int RATE_LIMIT_EXCEEDED = 429;
    private static final int MATCHES_TO_PROCESS = 10;

    private String player, team;
    private Context context;
    private Resources res;
    private OnMatchHistoryCompleteListener matchHistoryCompleteListener;

    public MatchHistoryAsync(String team, String player, Context context,
                             OnMatchHistoryCompleteListener matchHistoryCompleteListener) {
        this.player = player;
        this.context = context;
        this.team = team;
        res = context.getResources();
        this.matchHistoryCompleteListener = matchHistoryCompleteListener;
    }

    @Override
    protected List<Match> doInBackground(String... params) {
        List<Match> matches = new ArrayList<>();
        try {
            URL url = constructQuery();

            JSONRetriever retriever = new JSONRetriever(url);
            JSONObject jsonObject = retriever.retrieve();

            BasicMatchParser parser = new BasicMatchParser(MATCHES_TO_PROCESS);
            matches = parser.parseMatches(jsonObject.getJSONArray(MATCHES));
        } catch(IOException | JSONException e) {
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
        return new URL("https://na.api.pvp.net/api/lol/na/v2.2/matchlist/by-summoner/"
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
        matchHistoryCompleteListener.onCompleteListener(matchIDs);
    }

}
