package com.markgubatan.loltracker.tasks.riot;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.markgubatan.loltracker.GameObject;
import com.markgubatan.loltracker.utility.JSONFileRetriever;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

/**
 * AsyncTask to retrieve a String description and name for a GameObject
 */
public class StaticObjectRetriever extends AsyncTask<Integer, Void, GameObject>{

    private String mode;
    private int id;
    private Context context;
    private TextView name;
    private TextView description;
    private JSONFileRetriever jsonFileRetriever;

    public StaticObjectRetriever(String mode, int id, Context context, TextView name, TextView description) {
        this.mode = mode;
        this.id = id;
        this.context = context;
        this.name = name;
        this.description = description;
        jsonFileRetriever = new JSONFileRetriever();
    }

    @Override
    protected GameObject doInBackground(Integer... params) {
        GameObject gameObject = new GameObject();
        try {
            gameObject = getGameObject();
        }
        catch(IOException | JSONException e) {
            e.printStackTrace();
        }
        return gameObject;
    }

    private GameObject getGameObject() throws IOException, JSONException {
        switch(mode) {
            case "champion":
                return getChampion();
            case "item":
                return getItem();
        }
        return null;
    }

    private GameObject getChampion() throws IOException, JSONException {
        String json = jsonFileRetriever.jsonToStringFromAssetFolder("champion.json", context);
        JSONObject object = new JSONObject(json);
        JSONObject data = object.getJSONObject("data");
        Iterator keys = data.keys();
        String name = "";
        String desc = "";
        while(keys.hasNext()) {
            String key = (String)keys.next();
            JSONObject curObject = data.getJSONObject(key);
            int curKey = curObject.getInt("key");
            if(curKey == id) {
                name = curObject.getString("id");
                desc = curObject.getString("blurb");
            }
        }

        return new GameObject(name, desc);
    }

    private GameObject getItem() throws IOException, JSONException {
        String json = jsonFileRetriever.jsonToStringFromAssetFolder("item.json", context);
        JSONObject object = new JSONObject(json);
        JSONObject data = object.getJSONObject("data");
        Iterator keys = data.keys();
        String name = "";
        String desc = "";
        while(keys.hasNext()) {
            String key = (String)keys.next();
            JSONObject curObject = data.getJSONObject(key);
            if(Integer.parseInt(key) == id) {
                name = curObject.getString("name");
                desc = curObject.getString("description");
            }
        }

        return new GameObject(name, desc);
    }

    @Override
    protected void onPostExecute(GameObject gameObject) {
        name.setText(gameObject.getName());
        description.setText(gameObject.getDescription());
    }
}
