package com.markgubatan.loltracker.tasks;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.markgubatan.loltracker.Match;
import com.markgubatan.loltracker.R;
import com.markgubatan.loltracker.interfaces.OnMatchRetrievedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Asynchronous task to grab detailed information on a single match.
 */
public class MatchAsync extends AsyncTask<Long, Void, Match>{
    private static final String TAG = "MatchAsync";
    private static final String BASE_URL = "https://na.api.pvp.net/api/lol/na/v2.2/match/";
    private static final String BASE_API = "?api_key=";

    private Match match;
    private long id;
    private OnMatchRetrievedListener listener;
    private Context context;
    private Resources res;

    public MatchAsync(Match match, OnMatchRetrievedListener listener, Context context) {
        this.match = match;
        this.id = match.getMatchID();
        this.listener = listener;
        this.context = context;
        this.res = context.getResources();
    }

    @Override
    protected Match doInBackground(Long... params) {
        try {
            URL url = constructQuery();
            HTTPGetter getter = new HTTPGetter(url, TAG);
            InputStream in = getter.performRequest();
            if(in == null) return null;
            StreamToStringConverter converter = new StreamToStringConverter();
            String jsonString = converter.getString(in);
            JSONObject jsonObject = new JSONObject(jsonString);


        } catch(IOException | JSONException e) {
            e.printStackTrace();
        }


        return match;
    }

    private URL constructQuery() throws MalformedURLException {
        String apiKey = res.getString(R.string.riot_api_key);
        return new URL(BASE_URL + id + BASE_API + apiKey);

    }


    @Override
    protected void onPostExecute(Match match) {
        listener.onComplete(match);
    }
}
