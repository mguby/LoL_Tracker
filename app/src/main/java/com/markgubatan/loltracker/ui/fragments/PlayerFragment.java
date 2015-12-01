package com.markgubatan.loltracker.ui.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.markgubatan.loltracker.Match;
import com.markgubatan.loltracker.R;
import com.markgubatan.loltracker.listeners.OnMatchHistoryCompleteListener;
import com.markgubatan.loltracker.tasks.BitmapScaler;
import com.markgubatan.loltracker.tasks.riot.MatchHistoryAsync;
import com.markgubatan.loltracker.ui.adapters.MatchAdapter;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class PlayerFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TEAM = "team";
    private static final String PLAYER = "player";

    private String team;
    private String player;

    private FragmentManager fragmentManager;
    private MatchAdapter adapter;
    private ListView matchList;

    public static PlayerFragment newInstance(String team, String player) {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        args.putString(PLAYER, player);
        args.putString(TEAM, team);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PlayerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            team = getArguments().getString(TEAM);
            player = getArguments().getString(PLAYER);
        }



        fragmentManager = getActivity().getSupportFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);

        MatchHistoryAsync task = new MatchHistoryAsync(team, player, getActivity(),
                matchHistoryCompleteListener);
        task.execute();

        Bundle bundle = getArguments();
        if(bundle != null) {
            String teamLogoTransition = bundle.getString("LOGO_TRANSITION");
            String playerNameTransition = bundle.getString("NAME_TRANSITION");
            Log.e("endName", teamLogoTransition + " " + playerNameTransition);
            Bitmap logoBitmap = bundle.getParcelable("LOGO_BITMAP");

            ImageView teamLogo = (ImageView) view.findViewById(R.id.player_header_logo);
            teamLogo.setImageBitmap(logoBitmap);
            teamLogo.setTransitionName(teamLogoTransition);

            TextView playerName = (TextView) view.findViewById(R.id.player_header_name);
            playerName.setTransitionName(playerNameTransition);
            playerName.setText(player);
        }

        ImageView playerImage = (ImageView) view.findViewById(R.id.player_header_picture);
        BitmapScaler bitmapScaler = new BitmapScaler(getActivity());
        Bitmap playerBitmap = bitmapScaler.getImage("doublelift", 2);
        playerImage.setImageBitmap(playerBitmap);

        matchList = (ListView)view.findViewById(R.id.player_matches);
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String id);
    }

    protected OnMatchHistoryCompleteListener matchHistoryCompleteListener = new OnMatchHistoryCompleteListener() {
        @Override
        public void onCompleteListener(final List<Match> matches) {
            adapter = new MatchAdapter(player, team, matches, getActivity());
            matchList.setAdapter(adapter);
            matchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(position == 0 )
                        return;

                    fragmentManager.beginTransaction()
                            .replace(R.id.main_container, MatchFragment.newInstance(matches.get(position-1)))
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    };

}
