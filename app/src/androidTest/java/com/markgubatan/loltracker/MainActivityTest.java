package com.markgubatan.loltracker;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.widget.ListView;

import com.markgubatan.loltracker.ui.SlidingTabs.SlidingTabLayout;
import com.markgubatan.loltracker.ui.SlidingTabs.TabPagerAdapter;
import com.markgubatan.loltracker.ui.activities.MainActivity;
import com.markgubatan.loltracker.ui.adapters.OrganizationAdapter;
import com.markgubatan.loltracker.ui.adapters.TeamAdapter;
import com.markgubatan.loltracker.ui.fragments.LeaguesFragment;
import com.markgubatan.loltracker.ui.fragments.TeamsFragment;

/**
 * Basic tests for MainActivity.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>{

    private static final CharSequence titleArray[] = {"NALCS", "EULCS", "LCK", "LPL", "LMS"};
    private MainActivity mainActivity;
    private Fragment fragment;

    private LeaguesFragment leaguesFragment;
    private View leaguesView;
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private TabPagerAdapter tabPagerAdapter;

    private TeamsFragment naTeamsFragment;
    private View teamsView;
    private ListView teamsList;
    private TeamAdapter teamAdapter;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mainActivity = getActivity();
        fragment = mainActivity.getSupportFragmentManager().getFragments().get(0);
        leaguesFragment = (LeaguesFragment) fragment;
        leaguesView = leaguesFragment.getView();
        viewPager = (ViewPager) leaguesView.findViewById(R.id.viewpager);
        slidingTabLayout = (SlidingTabLayout) leaguesView.findViewById(R.id.sliding_tabs);
        tabPagerAdapter = (TabPagerAdapter) viewPager.getAdapter();
        naTeamsFragment = (TeamsFragment) tabPagerAdapter.getItem(0);
//        teamsView = naTeamsFragment.getView();
//        teamsList = (ListView) teamsView.findViewById(android.R.id.list);
//        teamAdapter = (TeamAdapter) teamsList.getAdapter();
    }

    /* Makes sure all views were retrieved correctly and are of the right type */
    @SmallTest
    public void testPreconditions() {
        assertNotNull("mainActivity was null", mainActivity);
        assertNotNull("leaguesFragment was null", leaguesFragment);
        assertNotNull("fragment was null", fragment);
        assertTrue("fragment wasn't a LeaguesFragment", fragment instanceof LeaguesFragment);
        assertNotNull("leaguesView was null", leaguesView);
        assertNotNull("viewPager was null", viewPager);
        assertNotNull("slidingTabLayout was null", slidingTabLayout);
        assertNotNull("tabPagerAdapter was null", tabPagerAdapter);
        assertNotNull("naTeamsFragment was null", naTeamsFragment);
//        assertNotNull("teamsView was null", teamsView);
//        assertNotNull("teamsList was null", teamsList);
//        assertNotNull("teamAdapter was null", teamAdapter);
    }

    @MediumTest
    public void testViewPager_titles() {
        assertTrue("Not all titles were in the viewPager", tabPagerAdapter.getCount() == titleArray.length);

        // Test to make sure each individual title was correct
        for(int i = 0; i < tabPagerAdapter.getCount(); i++) {
            CharSequence cur = titleArray[i];
            assertTrue(cur + " was not the title of the tab #" + i, cur.equals(tabPagerAdapter.getPageTitle(i)));
        }
    }

//    @MediumTest
//    public void testNaTeamsFragment_layout() {
//        Resources res = mainActivity.getResources();
//        int leagueID = res.getIdentifier("nalcs", "array", getActivity().getPackageName());
//        final String[] teams = res.getStringArray(leagueID);
//
//        assertTrue("not all teams were in listView", teams.length == teamAdapter.getCount());
//
//        // Test to make sure each individual team was correct
//        for(int i = 0; i < teams.length; i++) {
//            String cur = teams[i];
//            assertTrue(cur + " was not the title of tab #" + i, cur.equals(teamAdapter.getItem(i)));
//        }
//
//    }


}
