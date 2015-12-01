package com.markgubatan.loltracker.ui.fragments;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.markgubatan.loltracker.R;
import com.markgubatan.loltracker.ui.adapters.OrganizationAdapter;
import com.markgubatan.loltracker.ui.fragments.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class OrganizationFragment extends Fragment implements AbsListView.OnItemClickListener {

    private static final String TEAM = "team";
    private FragmentManager fragmentManager;
    private OnFragmentInteractionListener mListener;
    private String team;
    private String[] players;

    private AbsListView mListView;
    private OrganizationAdapter mAdapter;
    private View rootView;

    // TODO: Rename and change types of parameters
    public static OrganizationFragment newInstance(String team) {
        OrganizationFragment fragment = new OrganizationFragment();
        Bundle args = new Bundle();
        args.putString(TEAM, team);
        fragment.setArguments(args);
        return fragment;
    }

    public static OrganizationFragment newInstance() {
        return new OrganizationFragment();
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OrganizationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            team = getArguments().getString(TEAM);
            players = getPlayers(team);
        }

        mAdapter = new OrganizationAdapter(team, players, getActivity());
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    private String[] getPlayers(String team) {
        Resources res = getResources();
        String teamTrimmed = team.replace(' ', '_').toLowerCase();
        int teamID = res.getIdentifier(teamTrimmed, "array", getActivity().getPackageName());
        if(teamID == 0)
            return res.getStringArray(R.array.counter_logic_gaming);
        return res.getStringArray(teamID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_organization, container, false);

        Bundle bundle = getArguments();
        if(bundle != null) {
            String name = bundle.getString("TRANSITION_NAME");
            String logo = bundle.getString("TRANSITION_LOGO");
            Log.e("endLogo", logo);
            Log.e("endName", name);

            ImageView teamLogo = (ImageView)rootView.findViewById(R.id.team_header_logo);
            teamLogo.setTransitionName(logo);

            Bitmap logoBitmap = bundle.getParcelable("LOGO_BITMAP");
            teamLogo.setImageBitmap(logoBitmap);

            TextView teamName = (TextView)rootView.findViewById(R.id.team_header_name);
            teamName.setTransitionName(name);
            teamName.setText(bundle.getString(TEAM));
        }
        // Set the adapter
        mListView = (AbsListView) rootView.findViewById(R.id.org_list);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if(position == 0) {
//                    TextView bio = (TextView) view.findViewById(R.id.team_header_bio);
//                    int visibility = bio.getVisibility();
//                    if(visibility == View.GONE) {
//                        bio.setVisibility(View.VISIBLE);
//                    }
//                    else {
//                        bio.setVisibility(View.GONE);
//                    }
//                } else {
                String player = players[position];

                TransitionInflater ti = TransitionInflater.from(getActivity());
                setSharedElementReturnTransition(ti.inflateTransition(R.transition.image_transform));
                setExitTransition(ti.inflateTransition(android.R.transition.fade));

                PlayerFragment playerFragment = PlayerFragment.newInstance(team, players[position]);
                playerFragment.setSharedElementEnterTransition(ti.inflateTransition(R.transition.image_transform));
                playerFragment.setEnterTransition(ti.inflateTransition(android.R.transition.fade));

                String teamLogoTransition = player + "LogoTransition";
                String playerNameTransition = player + "NameTransition";
                ImageView teamLogo = (ImageView) rootView.findViewById(R.id.team_header_logo);
                teamLogo.setTransitionName(teamLogoTransition);
                TextView playerTextView = (TextView) view.findViewById(R.id.player_name);
                playerTextView.setTransitionName(playerNameTransition);

                Log.e("startName", teamLogoTransition + " " + playerNameTransition);

                Bundle bundle = playerFragment.getArguments();
                bundle.putString("LOGO_TRANSITION", teamLogoTransition);
                bundle.putString("NAME_TRANSITION", playerNameTransition);
                bundle.putParcelable("LOGO_BITMAP", ((BitmapDrawable) teamLogo.getDrawable()).getBitmap());
                playerFragment.setArguments(bundle);

                fragmentManager.beginTransaction()
                        .addSharedElement(teamLogo, teamLogoTransition)
                        .addSharedElement(playerTextView, playerNameTransition)
                        .replace(R.id.main_container, playerFragment)
                        .addToBackStack("TeamToPlayer")
                        .commit();
//                }
            }
        });


        return rootView;
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
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

}
