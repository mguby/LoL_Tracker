package com.markgubatan.loltracker.ui.fragments;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.markgubatan.loltracker.R;
import com.markgubatan.loltracker.ui.adapters.TeamAdapter;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class TeamsFragment extends Fragment {

    private static final String LEAGUE = "league";

    private FragmentManager fragmentManager;
    private String[] teams;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private TeamAdapter mAdapter;

    public static TeamsFragment newInstance(String league) {
        TeamsFragment fragment = new TeamsFragment();
        Bundle args = new Bundle();
        args.putString(LEAGUE, league);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TeamsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCompat.postponeEnterTransition(getActivity());

        if (getArguments() != null) {
            String league = getArguments().getString(LEAGUE);
            Log.d("Tag", league);
            setTeamList(league);
        }


        mAdapter = new TeamAdapter(teams, getActivity());

        fragmentManager = getActivity().getSupportFragmentManager();
    }

    private void setTeamList(String league) {
        Resources res = getResources();
        int leagueID = res.getIdentifier(league.toLowerCase(), "array", getActivity().getPackageName());
        teams = res.getStringArray(leagueID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(R.id.team_list);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String team = teams[position];
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    animateTransition(view, team);
                }
                else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_container, OrganizationFragment.newInstance(team))
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
        return view;
    }

    /**
     * Shared element transition from TeamsFragment to OrganizationFragment
     * @param view      ListView item that was clicked
     * @param team      Current team
     */
    private void animateTransition(View view, String team) {
        TransitionInflater ti = TransitionInflater.from(getActivity());
        setSharedElementReturnTransition(ti.inflateTransition(R.transition.image_transform));
        setExitTransition(ti.inflateTransition(android.R.transition.fade));

        Fragment orgFragment = OrganizationFragment.newInstance(team);
        orgFragment.setSharedElementEnterTransition(ti.inflateTransition(R.transition.image_transform));
        orgFragment.setEnterTransition(ti.inflateTransition(android.R.transition.fade));

        ImageView teamLogo = (ImageView) view.findViewById(R.id.general_logo);
        String logoTransition = teamLogo.getTransitionName();
        TextView teamName = (TextView) view.findViewById(R.id.general_name);
        String nameTransition = teamName.getTransitionName();

        Bundle bundle = orgFragment.getArguments();
        bundle.putString(getString(R.string.transition_logo), logoTransition);
        bundle.putString(getString(R.string.transition_name), nameTransition);
        bundle.putParcelable(getString(R.string.logo_bitmap),
                ((BitmapDrawable) teamLogo.getDrawable()).getBitmap());
        orgFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .addSharedElement(teamLogo, logoTransition)
                .addSharedElement(teamName, nameTransition)
                .replace(R.id.main_container, orgFragment)
                .addToBackStack("LeagueToTeam")
                .commit();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
