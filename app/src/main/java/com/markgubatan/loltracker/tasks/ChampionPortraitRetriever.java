package com.markgubatan.loltracker.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.markgubatan.loltracker.utility.JSONFileRetriever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Async task to retrieve the champion portrait bitmap from the Riot Games static data endpoint.
 */
public class ChampionPortraitRetriever extends AsyncTask<Integer, Void, Bitmap> {
    private final static String BASE_URL = "http://ddragon.leagueoflegends.com/cdn/5.22.2/img/champion/";
    private final static String EXTENSION = ".png";
    private final static String TAG = "ChampionPortraitAsync";

    private int id;
    private JSONFileRetriever jsonRetriever;
    private Context context;

    public ChampionPortraitRetriever(int id, Context context) {
        this.id = id;
        this.context = context;
        jsonRetriever = new JSONFileRetriever();
    }

    @Override
    protected Bitmap doInBackground(Integer... params) {
        Bitmap championBitmap = null;
        try {
            String champion = getChampionFromId();
            if(champion == null) return null;

            championBitmap = getBitmapFromStaticData(champion);
        }
        catch(IOException | JSONException e) {
            e.printStackTrace();
        }

        return championBitmap;
    }

    /**
     * Gets the champion name from the champion.json asset file using the provided id that was
     * retrieved during MatchHistoryAsync.java
     * @return                  Champion name from the provided id.
     * @throws IOException
     */
    private String getChampionFromId() throws IOException, JSONException {
        String championJson = jsonRetriever.jsonToStringFromAssetFolder("champion.json", context);
        JSONObject championsObject = new JSONObject(championJson);
        JSONArray championsArray = championsObject.getJSONArray("data");
        for(int i = 0; i < championsArray.length(); i++) {
            JSONObject curChampion = championsArray.getJSONObject(i);
            int curKey = curChampion.getInt("key");
            if(curKey == id) {
                return curChampion.getString("id");
            }
        }
        return null;
    }

    /**
     * Retrieves the bitmap for the current champion from the Riot Games Static Data API.
     * @param champion      name of the champion being searched for
     * @return              down sampled bitmap for the champion
     */
    private Bitmap getBitmapFromStaticData(String champion) throws IOException{
        URL url = new URL(BASE_URL + champion + EXTENSION);
        HTTPGetter getter = new HTTPGetter(url, TAG);
        InputStream in = getter.performRequest();
        return BitmapFactory.decodeStream(in);
    }
}
