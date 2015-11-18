package com.markgubatan.loltracker.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.markgubatan.loltracker.Match;
import com.markgubatan.loltracker.R;
import com.markgubatan.loltracker.listeners.OnMatchHistoryCompleteListener;
import com.markgubatan.loltracker.listeners.OnMatchRetrievedListener;
import com.markgubatan.loltracker.tasks.riot.MatchAsync;
import com.markgubatan.loltracker.ui.fragments.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class MatchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MATCH = "match";

    // TODO: Rename and change types of parameters
    private Match match;

    private OnFragmentInteractionListener mListener;
    private ListView listView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MatchFragment() {
    }

    // TODO: Rename and change types of parameters
    public static MatchFragment newInstance(Match match) {
        MatchFragment fragment = new MatchFragment();
//        Bundle args = new Bundle();
//        args.putParcelable(MATCH, match);
//        fragment.setArguments(args);
        fragment.setMatch(match);
        return fragment;
    }

    private void setMatch(Match match) {
        this.match = match;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
//            mParam1 = getArguments().getParcelable(MATCH);
        }

        MatchAsync matchAsync = new MatchAsync(match, listener, getActivity());
        matchAsync.execute();

        // TODO: Change Adapter to display your content
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
        public void onFragmentInteraction(String id);
    }

    protected OnMatchRetrievedListener listener = new OnMatchRetrievedListener() {
        @Override
        public void onComplete(Match match) {
//            listView.setAdapter();
        }
    };

}
