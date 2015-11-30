package com.markgubatan.loltracker.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.markgubatan.loltracker.R;
import com.markgubatan.loltracker.tasks.BitmapScaler;

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
    private BitmapScaler bitmapScaler;

    public OrganizationAdapter(String organizationName, String[] players, Context context) {
        this.organizationName = organizationName;
        this.players = players;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        bitmapScaler = new BitmapScaler(context);
    }
    @Override
    public int getCount() {
        return players.length;
    }

    @Override
    public Object getItem(int position) {
        return players[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        if(position == 0) {
//            return getHeaderView(convertView, parent);
//        }
//        else {
            return getRowView(position, convertView, parent);
//        }
    }

//    private View getHeaderView(View convertView, ViewGroup parent) {
//        HeaderViewHolder holder;
//        if(convertView == null) {
//            holder = new HeaderViewHolder();
//            convertView = inflater.inflate(R.layout.list_header_team, parent, false);
//
//            holder.logo = (ImageView) convertView.findViewById(R.id.team_header_logo);
//            holder.name = (TextView) convertView.findViewById(R.id.team_header_name);
//            holder.bio = (TextView) convertView.findViewById(R.id.team_header_bio);
//        }
//        else {
//            holder = (HeaderViewHolder) convertView.getTag();
//        }
//
//        convertView.setTag(holder);
//        String teamFile = organizationName.toLowerCase().replace(' ', '_') + "_logo";
//        Bitmap bitmap = bitmapScaler.getImage(teamFile, 4);
//        holder.logo.setImageBitmap(bitmap);
//        holder.name.setText(organizationName);
//        holder.bio.setText(R.string.example_bio);
//
//        return convertView;
//    }

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

        holder.name.setText(players[position]);
        holder.role.setText(ROLES[position]);
        return convertView;
    }

    private class HeaderViewHolder {
        ImageView logo;
        TextView name;
        TextView bio;
    }

    private class RowViewHolder {
        TextView role;
        TextView name;
    }
}
