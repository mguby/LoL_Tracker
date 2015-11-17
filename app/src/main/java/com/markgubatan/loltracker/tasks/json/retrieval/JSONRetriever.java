package com.markgubatan.loltracker.tasks.json.retrieval;

import com.markgubatan.loltracker.tasks.http.HTTPGetter;
import com.markgubatan.loltracker.tasks.http.StreamToStringConverter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Takes in a URL and returns the JSON associated with it in a JSONObject.
 */
public class JSONRetriever {
    private static final String TAG = "JSONRetriever";
    private final StreamToStringConverter converter = new StreamToStringConverter();
    private URL url;

    public JSONRetriever(URL url) {
        this.url = url;
    }

    /**
     * Retrieves JSONObject from the URL passed into the constructor.
     * @return                  JSONObject associated with the API call
     * @throws IOException
     * @throws JSONException
     */
    public JSONObject retrieve() throws IOException, JSONException {
        HTTPGetter getter = new HTTPGetter(url, TAG);
        InputStream in = getter.performRequest();
        if(in == null) return null;

        String jsonString = converter.getString(in);
        return new JSONObject(jsonString);
    }
}
