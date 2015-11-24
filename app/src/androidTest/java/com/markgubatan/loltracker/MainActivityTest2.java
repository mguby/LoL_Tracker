package com.markgubatan.loltracker;

import android.content.res.Resources;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import com.markgubatan.loltracker.ui.activities.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Espresso version of MainActivity tests. t
 */
public class MainActivityTest2 extends ActivityInstrumentationTestCase2<MainActivity> {

    private static final String TAG = "MainActivityTest2";
    private MainActivity mainActivity;
    private Resources res;
    private String[] naLCS;
    private String[] euLCS;
    private String[] lpl;
    private String[] lck;
    private String[] lms;

    public MainActivityTest2() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mainActivity = getActivity();
        res = mainActivity.getResources();
        naLCS = res.getStringArray(R.array.nalcs);
        euLCS = res.getStringArray(R.array.eulcs);
        lpl = res.getStringArray(R.array.lpl);
        lck = res.getStringArray(R.array.lck);
        lms = res.getStringArray(R.array.lms);
    }

    @SmallTest
    public void testPreconditions() {
        assertNotNull("mainActivity was null", mainActivity);
    }

    @MediumTest
    public void testLeaguesFragment_displayed() {
        onView(withId(R.id.main_container)).check(matches(isDisplayed()));
        checkTeams(naLCS);
        onView(withId(R.id.viewpager)).perform(swipeLeft());
        checkTeams(euLCS);
        onView(withId(R.id.viewpager)).perform(swipeLeft());
        checkTeams(lck);
        onView(withId(R.id.viewpager)).perform(swipeLeft());
        checkTeams(lpl);
        onView(withId(R.id.viewpager)).perform(swipeLeft());
        checkTeams(lms);
    }

    private void checkTeams(String[] teams) {
        for(String team : teams) {
            onData(allOf( is(instanceOf(String.class)), withName(team) ))
                    .inAdapterView(allOf(withId(R.id.team_list), isDisplayed()))
                    .check(matches(isDisplayed()));
        }
    }

    @MediumTest
    public void testTeamsFragment_teamLineups() {
        onView(withId(R.id.main_container)).check(matches(isDisplayed()));
        checkLineups(naLCS);
//        onView(withId(R.id.viewpager)).perform(swipeLeft());
//        checkLineups(euLCS);
//        onView(withId(R.id.viewpager)).perform(swipeLeft());
//        checkLineups(lck);
//        onView(withId(R.id.viewpager)).perform(swipeLeft());
//        checkLineups(lpl);
//        onView(withId(R.id.viewpager)).perform(swipeLeft());
//        checkLineups(lms);
    }

    private void checkLineups(String[] teams) {
        for(String team : teams) {
            Log.v(TAG, team);
            onData(allOf(is(instanceOf(String.class)), withName(team)))
                    .inAdapterView(allOf(withId(R.id.team_list), isDisplayed()))
                    .check(matches(isDisplayed()))
                    .perform(click());

            String[] players = getPlayers(team);
            checkPlayers(players);
            pressBack();
        }
    }

    private String[] getPlayers(String team) {
        String teamReplace = team.replace(' ', '_').toLowerCase();
        int id = res.getIdentifier(teamReplace, "array", mainActivity.getPackageName());
        return res.getStringArray(id);
    }

    private void checkPlayers(String[] players) {
        for(String player : players) {
            onData(allOf(is(instanceOf(String.class)), withName(player) ))
                    .inAdapterView(withId(R.id.org_list))
                    .check(matches(isDisplayed()));
        }
    }

    @MediumTest
    public void testPlayerFragment_layout() {
        onData(allOf(is(instanceOf(String.class)), withName("Counter Logic Gaming")))
                .inAdapterView(allOf(withId(R.id.team_list), isDisplayed()))
                .check(matches(isDisplayed()))
                .perform(click());

        onData(allOf(is(instanceOf(String.class)), withName("Doublelift")))
                .inAdapterView(withId(R.id.org_list))
                .check(matches(isDisplayed()))
                .perform(click());

        onData(is(instanceOf(Match.class)))
                .inAdapterView(withId(R.id.player_matches))
                .atPosition(1)
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.match_info))
                .check(matches(isDisplayed()));
    }

    public static Matcher<Object> withName(String name) {
        return withName(equalTo(name));
    }

    public static Matcher<Object> withName(final Matcher<String> name) {
        return new BoundedMatcher<Object, String>(String.class) {
            @Override
            protected boolean matchesSafely(String s) {
                return name.matches(s);
            }

            @Override
            public void describeTo(Description description) {
                name.describeTo(description);
            }
        };
    }

}
