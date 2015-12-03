package com.markgubatan.loltracker.ui.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.markgubatan.loltracker.R;
import com.markgubatan.loltracker.tasks.riot.StaticObjectRetriever;
import com.markgubatan.loltracker.tasks.riot.StaticPortraitRetriever;

/**
 * A fragment that will display detailed information on game objects such as champions, spells, or
 * items.
 */
public class GameObjectFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ID = "id";
    private static final String ARG_MODE = "mode";

    private int id;
    private String mode;

    private TextView name;
    private TextView description;


    /**
     * Creates new instance of GameObjectFragment from either a champion/spell/item id.
     *
     * @param id id of game object
     * @return A new instance of fragment GameObjectFragment.
     */
    public static GameObjectFragment newInstance(int id, String mode) {
        GameObjectFragment fragment = new GameObjectFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        args.putString(ARG_MODE, mode);
        fragment.setArguments(args);
        return fragment;
    }

    public GameObjectFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_ID);
            mode = getArguments().getString(ARG_MODE);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_object, container, false);

        ImageView bitmap = (ImageView) view.findViewById(R.id.game_object_image);
        StaticPortraitRetriever portraitRetriever =
                new StaticPortraitRetriever(mode, id, getActivity(), bitmap);
        portraitRetriever.execute();

        name = (TextView) view.findViewById(R.id.game_object_name);
        description = (TextView) view.findViewById(R.id.game_object_description);
        StaticObjectRetriever objectRetriever =
                new StaticObjectRetriever(mode, id, getActivity(), name, description);
        objectRetriever.execute();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
