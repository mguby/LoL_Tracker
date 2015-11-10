package com.markgubatan.loltracker.tasks;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class that Async tasks use to perform HTTP GET requests.
 */
public class HTTPGetter {
    private static final int RATE_LIMIT_EXCEEDED = 429;

    private URL url;
    private String tag;

    public HTTPGetter(URL url, String tag) {
        this.url = url;
        this.tag = tag;
    }

    /**
     * Performs the API request based on the URL created from the query
     * @return              InputStream of the request
     * @throws IOException
     */
    public InputStream performRequest() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if(responseCode == RATE_LIMIT_EXCEEDED) {
            Log.e(tag, "Rate Limit Exceeded");
            return null;
        }
        return new BufferedInputStream(connection.getInputStream());
    }
}
