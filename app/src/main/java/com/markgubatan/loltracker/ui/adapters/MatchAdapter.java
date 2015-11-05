package com.markgubatan.loltracker.ui.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.markgubatan.loltracker.R;

/**
 * Created by Mark on 11/4/2015.
 */
public class MatchAdapter extends BaseAdapter {
    private final static String TAG = "OrganizationAdapter";

    private String playerName;
    private String teamName;
    private String[] matches;
    private Context context;
    private LayoutInflater inflater;

    public MatchAdapter(String playerName, String teamName, String[] matches, Context context) {
        this.playerName = playerName;
        this.teamName = teamName;
        this.matches = matches;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return matches.length + 1;
    }

    @Override
    public Object getItem(int position) {
        return matches[position - 1];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(position == 0) {
            return getHeaderView(convertView, parent);
        }
        else {
            return getRowView(position, convertView, parent);
        }
    }

    private View getHeaderView(View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if(convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.list_header_match, parent, false);

            holder.logo = (ImageView) convertView.findViewById(R.id.player_header_logo);
            holder.picture = (ImageView) convertView.findViewById(R.id.player_header_picture);
            holder.name = (TextView)convertView.findViewById(R.id.player_header_name);
        }
        else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        convertView.setTag(holder);
        teamName = teamName.toLowerCase().replace(' ', '_') + "_logo";

        holder.logo.setImageBitmap(getImage(teamName, 4));
//        holder.picture.setImageBitmap(getImage(playerName.toLowerCase()));
        holder.picture.setImageBitmap(getImage("doublelift", 2));
        holder.name.setText(playerName);

        return convertView;
    }

    private View getRowView(int position, View convertView, ViewGroup parent) {
        RowViewHolder holder;
        if(convertView == null) {
            holder = new RowViewHolder();
            convertView = inflater.inflate(R.layout.list_item_match, parent, false);

            holder.match = (TextView) convertView.findViewById(R.id.match);
        }
        else {
            holder = (RowViewHolder) convertView.getTag();
        }

        // Need to set the tag or else the ListView elements will randomly order themselves
        convertView.setTag(holder);

        holder.match.setText(matches[position - 1]);
        return convertView;
    }

    private Bitmap getImage(String name, int sampleSize) {
        Log.d(TAG, "getTeamImage " + name);
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


    private class HeaderViewHolder {
        ImageView picture;
        ImageView logo;
        TextView name;
    }

    private class RowViewHolder {
        TextView match;
    }
}
