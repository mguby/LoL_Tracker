package com.markgubatan.loltracker.tasks;

import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Mark Gubatan on 11/16/2015.
 */
public class StreamToStringConverter {

    public StreamToStringConverter() {

    }

    /**
     * Creates String response of matches to be parsed from the InputStream created in
     * performRequest;
     * @param in            InputStream returned from performRequest
     * @return              JSON returned from the InputStream in String form
     * @throws IOException
     */
    @NonNull
    public String getString(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"), 10000);
        StringBuilder builder = new StringBuilder();
        String line;

        while((line = reader.readLine()) != null) {
            line = line + "\n";
            builder.append(line);
        }
        return builder.toString();
    }
}
