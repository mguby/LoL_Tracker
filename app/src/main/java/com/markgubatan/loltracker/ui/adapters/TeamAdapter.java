package com.markgubatan.loltracker.ui.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;

import com.markgubatan.loltracker.R;

/**
 * Adapter to be used for Team and Player lists.
 */
public class TeamAdapter extends BaseAdapter{
    private final static String TAG = "TeamAdapter";

    private String[] teams;
    private Context context;
    private LayoutInflater inflater;

    public TeamAdapter(String[] teams, Context context) {
        this.teams = teams;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return teams.length;
    }

    @Override
    public Object getItem(int position) {
        return teams[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item_team, parent, false);

            holder.logo = (ImageView) convertView.findViewById(R.id.general_logo);
            holder.name = (TextView) convertView.findViewById(R.id.general_name);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Need to set the tag or else the ListView elements will randomly order themselves
        convertView.setTag(holder);

        String team= teams[position];
        holder.name.setText(team);
        holder.logo.setImageBitmap(getTeamImage(team));
        return convertView;
    }

    private Bitmap getTeamImage(String team) {
        Log.d(TAG, "getTeamImage " + team);
        Resources res = context.getResources();
        team = team.toLowerCase().replace(' ', '_') + "_logo";
        int id = res.getIdentifier(team, "drawable", context.getPackageName());
        if(id == 0)
            id = res.getIdentifier("team_liquid_logo", "drawable", context.getPackageName());
        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = calculateInSampleSize(options, 100, 100);
        options.inSampleSize = 4;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, id, options);
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private class ViewHolder {
        ImageView logo;
        TextView name;
    }
}
