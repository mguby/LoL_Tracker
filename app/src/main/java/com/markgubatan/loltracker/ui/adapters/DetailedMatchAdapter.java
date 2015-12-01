package com.markgubatan.loltracker.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.markgubatan.loltracker.Match;
import com.markgubatan.loltracker.Player;
import com.markgubatan.loltracker.R;
import com.markgubatan.loltracker.tasks.json.retrieval.JSONRetriever;
import com.markgubatan.loltracker.tasks.riot.StaticPortraitRetriever;
import com.markgubatan.loltracker.utility.JSONFileRetriever;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Custom ListView adapter for displaying the players for a certain match
 */
public class DetailedMatchAdapter extends BaseAdapter {

    private static final int NUM_PLAYERS = 10;
    private static final String TAG = "DMA";
    private Match match;
    private Context context;
    private LayoutInflater inflater;
    private JSONObject json;

    public DetailedMatchAdapter(Match match, Context context) throws JSONException, IOException {
        this.match = match;
        this.context = context;
        inflater = LayoutInflater.from(context);

        JSONFileRetriever retriever = new JSONFileRetriever();
        String jsonStr = retriever.jsonToStringFromAssetFolder("champion.json", context);
        json = new JSONObject(jsonStr);
    }

    @Override
    public int getCount() {
        return NUM_PLAYERS;
    }

    /**
     * Gets the player that should be displayed at position. There are 10 spots and 2 teams so we
     * have to get the player for the correct team based on which position is being retrieved.
     * @param position      position in list view
     * @return              player associated with that position
     */
    @Override
    public Object getItem(int position) {
        if(position < 5) {
            return match.getBlueTeam().get(position);
        } else {
            return match.getRedTeam().get(position - 5);
        }
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
            convertView = inflater.inflate(R.layout.list_item_match_info, parent, false);

            holder.champion = (ImageView) convertView.findViewById(R.id.champion);
            holder.summonerName = (TextView) convertView.findViewById(R.id.summoner_name);
            holder.kda = (TextView) convertView.findViewById(R.id.kda);
            holder.spell1 = (ImageView) convertView.findViewById(R.id.spell_1);
            holder.spell2 = (ImageView) convertView.findViewById(R.id.spell_2);
            holder.item0 = (ImageView) convertView.findViewById(R.id.item_0);
            holder.item1 = (ImageView) convertView.findViewById(R.id.item_1);
            holder.item2 = (ImageView) convertView.findViewById(R.id.item_2);
            holder.item3 = (ImageView) convertView.findViewById(R.id.item_3);
            holder.item4 = (ImageView) convertView.findViewById(R.id.item_4);
            holder.item5 = (ImageView) convertView.findViewById(R.id.item_5);
            holder.item6 = (ImageView) convertView.findViewById(R.id.item_6);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        convertView.setTag(holder);
        Player cur = (Player) getItem(position);
        nullBitmaps(holder);
        retrieveBitmaps(cur, holder);

        holder.summonerName.setText(cur.getSummonerName());
        Log.e(TAG, cur.getSummonerName());

        String k = Integer.toString(cur.getKills());
        String d = Integer.toString(cur.getDeaths());
        String a = Integer.toString(cur.getAssists());
        holder.kda.setText(k + "/" + d + "/" + a);



        holder.champion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }

    /** We null the bitmaps so that the transition is a bit smoother **/
    private void nullBitmaps(ViewHolder holder) {
        holder.champion.setImageBitmap(null);
        holder.spell1.setImageBitmap(null);
        holder.spell2.setImageBitmap(null);
        holder.item0.setImageBitmap(null);
        holder.item1.setImageBitmap(null);
        holder.item2.setImageBitmap(null);
        holder.item3.setImageBitmap(null);
        holder.item4.setImageBitmap(null);
        holder.item5.setImageBitmap(null);
        holder.item6.setImageBitmap(null);
    }

    /**
     * Create and run all async tasks for retrieving bitmaps.
     * @param cur       current player whose bitmaps are being retrieved
     * @param holder    viewholder for the current player
     */
    private void retrieveBitmaps(Player cur, ViewHolder holder) {
        StaticPortraitRetriever champRetriever = new StaticPortraitRetriever("champion", cur.getChampionId(), context, holder.champion);
        champRetriever.execute();
        StaticPortraitRetriever spell1Retriever = new StaticPortraitRetriever("spell", cur.getSpell1Id(), context, holder.spell1);
        spell1Retriever.execute();
        StaticPortraitRetriever spell2Retriever = new StaticPortraitRetriever("spell", cur.getSpell2Id(), context, holder.spell2);
        spell2Retriever.execute();
        StaticPortraitRetriever retriever0 = new StaticPortraitRetriever("item", cur.getItem0(), context, holder.item0);
        retriever0.execute();
        StaticPortraitRetriever retriever1 = new StaticPortraitRetriever("item", cur.getItem1(), context, holder.item1);
        retriever1.execute();
        StaticPortraitRetriever retriever2 = new StaticPortraitRetriever("item", cur.getItem2(), context, holder.item2);
        retriever2.execute();
        StaticPortraitRetriever retriever3 = new StaticPortraitRetriever("item", cur.getItem3(), context, holder.item3);
        retriever3.execute();
        StaticPortraitRetriever retriever4 = new StaticPortraitRetriever("item", cur.getItem4(), context, holder.item4);
        retriever4.execute();
        StaticPortraitRetriever retriever5 = new StaticPortraitRetriever("item", cur.getItem5(), context, holder.item5);
        retriever5.execute();
        StaticPortraitRetriever retriever6 = new StaticPortraitRetriever("item", cur.getItem6(), context, holder.item6);
        retriever6.execute();
    }

    private class ViewHolder {
        ImageView champion;
        TextView summonerName;
        TextView kda;
        ImageView spell1;
        ImageView spell2;
        ImageView item0;
        ImageView item1;
        ImageView item2;
        ImageView item3;
        ImageView item4;
        ImageView item5;
        ImageView item6;
    };
}
