package com.markgubatan.loltracker.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.markgubatan.loltracker.Match;
import com.markgubatan.loltracker.R;
import com.markgubatan.loltracker.listeners.OnMatchHistoryCompleteListener;
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

    private OnFragmentInteractionListener mListener;
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

        MatchHistoryAsync task = new MatchHistoryAsync(team, player, getActivity(),
                matchHistoryCompleteListener);
        task.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);
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
        mListener = null;
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
        public void onCompleteListener(List<Match> matches) {
            adapter = new MatchAdapter(player, team, matches, getActivity());
            matchList.setAdapter(adapter);
        }
    };

}
