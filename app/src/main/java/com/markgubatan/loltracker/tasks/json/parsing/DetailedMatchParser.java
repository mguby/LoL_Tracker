package com.markgubatan.loltracker.tasks.json.parsing;

import com.markgubatan.loltracker.Match;
import com.markgubatan.loltracker.Player;
import com.markgubatan.loltracker.tasks.riot.ApiConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses more detailed information on a single match
 */
public class DetailedMatchParser {

    private Match match;

    public DetailedMatchParser(Match match) {
        this.match = match;
    }

    public Match parse(JSONObject jsonMatch) throws JSONException {
        match.setDuration(jsonMatch.getInt(ApiConstants.DURATION));

        JSONArray participants = jsonMatch.getJSONArray(ApiConstants.PARTICIPANTS);
        parseParticipants(participants);

        JSONArray participantIdentities = jsonMatch.getJSONArray(ApiConstants.PARTICIPANT_IDENTITIES);
        parseIdentities(participantIdentities);

        JSONArray teams = jsonMatch.getJSONArray(ApiConstants.TEAMS);
        setWinner(teams);
        return match;
    }

    private void parseParticipants(JSONArray participants) throws JSONException {
        List<Player> blueTeam = new ArrayList<>();
        List<Player> redTeam = new ArrayList<>();

        for(int i = 0; i < participants.length(); i++) {
            JSONObject cur = participants.getJSONObject(i);
            Player player = parsePlayer(cur);

            if(cur.getInt(ApiConstants.TEAM_ID) == 100)
                blueTeam.add(player);
            else
                redTeam.add(player);

        }

        match.setBlueTeam(blueTeam);
        match.setRedTeam(redTeam);
    }

    private Player parsePlayer(JSONObject cur) throws JSONException {
        Player player = new Player();

        player.setSpell1Id(cur.getInt(ApiConstants.SPELL1_ID));
        player.setSpell2Id(cur.getInt(ApiConstants.SPELL2_ID));
        player.setChampionId(cur.getInt(ApiConstants.CHAMPION_ID));
        player.setHighestAchievedSeasonTier(ApiConstants.HIGHEST_TIER);

        JSONObject stats = cur.getJSONObject(ApiConstants.STATS);
        player.setChampLevel(stats.getInt(ApiConstants.CHAMP_LEVEL));
        player.setItem0(stats.getInt(ApiConstants.ITEM_0));
        player.setItem1(stats.getInt(ApiConstants.ITEM_1));
        player.setItem2(stats.getInt(ApiConstants.ITEM_2));
        player.setItem3(stats.getInt(ApiConstants.ITEM_3));
        player.setItem4(stats.getInt(ApiConstants.ITEM_4));
        player.setItem5(stats.getInt(ApiConstants.ITEM_5));
        player.setItem6(stats.getInt(ApiConstants.ITEM_6));

        player.setKills(stats.getInt(ApiConstants.KILLS));
        player.setLargestSpree(stats.getInt(ApiConstants.LARGEST_SPREE));
        player.setDeaths(stats.getInt(ApiConstants.DEATHS));
        player.setAssists(stats.getInt(ApiConstants.ASSISTS));
        player.setMinionsKilled(stats.getInt(ApiConstants.MINIONS_KILLED));

        player.setParticipantId(cur.getInt(ApiConstants.PARTICIPANT_ID));

        return player;
    }

    private void parseIdentities(JSONArray ids) throws JSONException {
        List<Player> blueTeam = match.getBlueTeam();
        List<Player> redTeam = match.getRedTeam();
        for(int i = 0; i < 10; i++) {
            Player player;
            if(i < 5)
                player = blueTeam.get(i);
            else
                player = redTeam.get(i - 5);

            JSONObject cur = ids.getJSONObject(i);
            JSONObject curPlayer = cur.getJSONObject(ApiConstants.PLAYER);

            player.setSummonerName(curPlayer.getString(ApiConstants.SUMMONER_NAME));
        }
    }

    private void setWinner(JSONArray teams) throws JSONException {
        Boolean blueWinner = teams.getJSONObject(0).getBoolean(ApiConstants.WINNER);
        if(blueWinner)
            match.setWinner("blue");
        else
            match.setWinner("red");
    }

}
