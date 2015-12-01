package com.markgubatan.loltracker.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.markgubatan.loltracker.Match;
import com.markgubatan.loltracker.R;
import com.markgubatan.loltracker.tasks.BitmapScaler;
import com.markgubatan.loltracker.tasks.riot.StaticPortraitRetriever;

import java.util.List;

/**
 * Adapter for list of matches to be used in PlayerFragment.
 */
public class MatchAdapter extends BaseAdapter {
    private final static String TAG = "OrganizationAdapter";

    private String playerName;
    private String teamName;
    private List<Match> matches;
    private Context context;
    private LayoutInflater inflater;
    private BitmapScaler bitmapScaler;

    public MatchAdapter(String playerName, String teamName, List<Match> matches, Context context) {
        this.playerName = playerName;
        this.teamName = teamName;
        this.matches = matches;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        bitmapScaler = new BitmapScaler(context);
    }
    @Override
    public int getCount() {
        return matches.size();
    }

    @Override
    public Object getItem(int position) {
        return matches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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
//            convertView = inflater.inflate(R.layout.list_header_match, parent, false);
//
//            holder.logo = (ImageView) convertView.findViewById(R.id.player_header_logo);
//            holder.picture = (ImageView) convertView.findViewById(R.id.player_header_picture);
//            holder.name = (TextView)convertView.findViewById(R.id.player_header_name);
//        }
//        else {
//            holder = (HeaderViewHolder) convertView.getTag();
//        }
//
//        convertView.setTag(holder);
//        teamName = teamName.toLowerCase().replace(' ', '_') + "_logo";
//        Bitmap teamBitmap = bitmapScaler.getImage(teamName, 4);
//        holder.logo.setImageBitmap(teamBitmap);
////        holder.picture.setImageBitmap(getImage(playerName.toLowerCase()));
//        Bitmap playerBitmap = bitmapScaler.getImage("doublelift", 2);
//        holder.picture.setImageBitmap(playerBitmap);
//        holder.name.setText(playerName);
//
//        return convertView;
//    }

    private View getRowView(int position, View convertView, ViewGroup parent) {
        RowViewHolder holder;
        if(convertView == null) {
            holder = new RowViewHolder();
            convertView = inflater.inflate(R.layout.list_item_match, parent, false);

            holder.match = (TextView) convertView.findViewById(R.id.match);
            holder.queue = (TextView) convertView.findViewById(R.id.queue);
            holder.champion = (ImageView) convertView.findViewById(R.id.match_list_champion);
        }
        else {
            holder = (RowViewHolder) convertView.getTag();
        }

        // Need to set the tag or else the ListView elements will randomly order themselves
        convertView.setTag(holder);

        Match match = matches.get(position);
        holder.match.setText(match.getDate());
        holder.queue.setText(match.getQueue());
        holder.champion.setImageBitmap(null);
        StaticPortraitRetriever retriever = new StaticPortraitRetriever(
                "champion", match.getChampion(), context, holder.champion);
        retriever.execute();
        return convertView;
    }

    private class HeaderViewHolder {
        ImageView picture;
        ImageView logo;
        TextView name;
    }

    private class RowViewHolder {
        TextView match;
        TextView queue;
        ImageView champion;
    }
}
