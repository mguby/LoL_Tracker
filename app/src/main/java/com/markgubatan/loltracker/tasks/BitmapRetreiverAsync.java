package com.markgubatan.loltracker.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Task in order to retrieve and downsample a bitmap asynchronously.
 */
public class BitmapRetreiverAsync extends AsyncTask<String, Void, Bitmap>{

    private ImageView view;
    private Context context;
    private String name;
    private int sampleSize;
    private BitmapScaler bitmapScaler;

    public BitmapRetreiverAsync(ImageView view, Context context, String name, int sampleSize) {
        this.view = view;
        this.context = context;
        this.name = name;
        this.sampleSize = sampleSize;
        bitmapScaler = new BitmapScaler(context);
    }


    @Override
    protected Bitmap doInBackground(String... params) {
        return bitmapScaler.getImage(name, sampleSize);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }
}
