package com.markgubatan.loltracker.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;

import com.markgubatan.loltracker.R;
import com.markgubatan.loltracker.tasks.BitmapRetreiverAsync;
import com.markgubatan.loltracker.tasks.BitmapScaler;

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
        String teamFile = team.toLowerCase().replace(' ', '_') + "_logo";

        BitmapRetreiverAsync task = new BitmapRetreiverAsync(holder.logo, context, teamFile, 4);
        task.execute();

        return convertView;
    }



    private class ViewHolder {
        ImageView logo;
        TextView name;
    }
}
