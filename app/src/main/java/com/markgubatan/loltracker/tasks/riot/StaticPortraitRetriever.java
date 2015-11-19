package com.markgubatan.loltracker.tasks.riot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.markgubatan.loltracker.tasks.http.HTTPGetter;
import com.markgubatan.loltracker.utility.JSONFileRetriever;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

/**
 * Async task to retrieve the portrait bitmap from the Riot Games static data endpoint.
 */
public class StaticPortraitRetriever extends AsyncTask<Integer, Void, Bitmap> {
    private final static String CHAMP_URL = "http://ddragon.leagueoflegends.com/cdn/5.22.2/img/champion/";
    private final static String ITEM_URL = "http://ddragon.leagueoflegends.com/cdn/5.22.3/img/item/";
    private final static String SPELL_URL = "http://ddragon.leagueoflegends.com/cdn/5.22.3/img/spell/";
    private final static String EXTENSION = ".png";
    private final static String TAG = "ChampionPortraitAsync";

    private int id;
    private JSONFileRetriever jsonRetriever;
    private Context context;
    private ImageView imageView;
    private String baseURL;
    private String object;
    private String mode;

    public StaticPortraitRetriever(String mode, int id, Context context, ImageView imageView) {
        this.id = id;
        this.context = context;
        jsonRetriever = new JSONFileRetriever();
        this.imageView = imageView;
        this.mode = mode;
    }

    @Override
    protected Bitmap doInBackground(Integer... params) {
        Bitmap bitmap = null;
        try {
            selectParameters(mode);

            bitmap = getBitmapFromStaticData();
        }
        catch(IOException | JSONException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private void selectParameters(String mode) throws IOException, JSONException {
        switch(mode) {
            case "champion":
                baseURL = CHAMP_URL;
                object = getObjectFromId("champion.json");
                break;
            case "item":
                baseURL = ITEM_URL;
                object = Integer.toString(id);
                break;
            case "spell":
                baseURL = SPELL_URL;
                object = getObjectFromId("spell.json");
                break;
        }
    }

    /**
     * Gets the object name from either the champion.json or spell.json asset file using
     * the provided id that was retrieved during MatchHistoryAsync.java
     * @return                  Champion name from the provided id.
     * @throws IOException
     */
    private String getObjectFromId(String file) throws IOException, JSONException {
        String json = jsonRetriever.jsonToStringFromAssetFolder(file, context);
        JSONObject object = new JSONObject(json);
        JSONObject data = object.getJSONObject("data");
        Iterator keys = data.keys();
        while(keys.hasNext()) {
            String key = (String)keys.next();
            JSONObject curObject = data.getJSONObject(key);
            int curKey = curObject.getInt("key");
            if(curKey == id) {
                return curObject.getString("id");
            }
        }
        return null;
    }


    /**
     * Retrieves the bitmap for the current bitmap from the Riot Games Static Data API.
     * @return              down sampled bitmap for the object
     */
    private Bitmap getBitmapFromStaticData() throws IOException{
        URL url = new URL(baseURL + object + EXTENSION);
        HTTPGetter getter = new HTTPGetter(url, TAG);
        InputStream in = getter.performRequest();
        return BitmapFactory.decodeStream(in);
    }

    @Override
    protected void onPostExecute(Bitmap champion) {
        imageView.setImageBitmap(champion);
    }
}
