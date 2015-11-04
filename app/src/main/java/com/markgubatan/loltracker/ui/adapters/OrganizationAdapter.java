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
 * ListView adapter for a team's list of players.
 */
public class OrganizationAdapter extends BaseAdapter{
    private final static String TAG = "OrganizationAdapter";
    private final static String[] ROLES = {"TOP", "JUNGLE", "MID", "AD CARRY", "SUPPORT"};

    private String organizationName;
    private String[] players;
    private Context context;
    private LayoutInflater inflater;

    public OrganizationAdapter(String organizationName, String[] players, Context context) {
        this.organizationName = organizationName;
        this.players = players;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return players.length + 1;
    }

    @Override
    public Object getItem(int position) {
        return players[position - 1];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(position == 0) {
            return getHeaderView(position, convertView, parent);
        }
        else {
            return getRowView(position, convertView, parent);
        }
    }

    private View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if(convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.list_header_team, parent, false);

            holder.logo = (ImageView) convertView.findViewById(R.id.team_header_logo);
            holder.bio = (TextView) convertView.findViewById(R.id.team_header_bio);
        }
        else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        convertView.setTag(holder);
        holder.logo.setImageBitmap(getTeamImage(organizationName));
        holder.bio.setText(organizationName);

        return convertView;
    }

    private View getRowView(int position, View convertView, ViewGroup parent) {
        RowViewHolder holder;
        if(convertView == null) {
            holder = new RowViewHolder();
            convertView = inflater.inflate(R.layout.list_item_player, parent, false);

            holder.role = (TextView) convertView.findViewById(R.id.player_role);
            holder.name = (TextView) convertView.findViewById(R.id.player_name);
        }
        else {
            holder = (RowViewHolder) convertView.getTag();
        }

        // Need to set the tag or else the ListView elements will randomly order themselves
        convertView.setTag(holder);

        holder.name.setText(players[position - 1]);
        holder.role.setText(ROLES[position - 1]);
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
        options.inSampleSize = 4;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, id, options);
    }


    private class HeaderViewHolder {
        ImageView logo;
        TextView bio;
    }

    private class RowViewHolder {
        TextView role;
        TextView name;
    }
}
