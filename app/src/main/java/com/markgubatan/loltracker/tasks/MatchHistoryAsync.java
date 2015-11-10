package com.markgubatan.loltracker.tasks;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.markgubatan.loltracker.R;

import java.util.List;

/**
 * Asynchronous task to retrieve a player's match history using the Riot Games API.
 * Riot's API requires a player's ID rather than a player's name to retrieve their
 * match history and thus we first have to convert our summoner's name into an ID from
 * our String Array resource.
 */
public class MatchHistoryAsync extends AsyncTask<String, Void, List<Integer>> {
    private String player;
    private String team;
    private Context context;
    private Resources res;

    public MatchHistoryAsync(String team, String player, Context context) {
        this.player = player;
        this.context = context;
        this.team = team;
        res = context.getResources();
    }

    @Override
    protected List<Integer> doInBackground(String... params) {
        String query = constructQuery();

        return null;
    }

    /**
     * Constructs query to be used in the API request.
     * @return      HTTP query
     */
    private String constructQuery() {
        String summonerID = getSummonerIDFromName();
        String apiKey = res.getString(R.string.riot_api_key);
        return "https://https://na.api.pvp.net/api/lol/na/v2.2/matchlist/by-summoner/"
                + summonerID + "?api_key=" + apiKey;
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
    protected void onPostExecute(List<Integer> matchIDs) {

    }
}
