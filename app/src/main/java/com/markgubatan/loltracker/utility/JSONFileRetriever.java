package com.markgubatan.loltracker.utility;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Retrieves a JSON file from the assets folder
 */
public class JSONFileRetriever {

    public JSONFileRetriever() {
    }

    public static String jsonToStringFromAssetFolder(String fileName,Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(fileName);

        byte[] data = new byte[file.available()];
        file.read(data);
        file.close();
        return new String(data);
    }
}
