package com.markgubatan.loltracker.tasks;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Created by Mark on 11/5/2015.
 */
public class BitmapScaler {

    private static final String TAG = "BitmapScaler";
    private Context context;

    public BitmapScaler(Context context) {
        this.context = context;
    }

    public Bitmap getImage(String name, int sampleSize) {
        Log.d(TAG, "getImage " + name);
        Resources res = context.getResources();
        int id = res.getIdentifier(name, "drawable", context.getPackageName());
        if(id == 0)
            id = res.getIdentifier("team_liquid_logo", "drawable", context.getPackageName());
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, id, options);
    }
}
