package com.markgubatan.loltracker;

import android.content.res.Resources;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;

import com.markgubatan.loltracker.ui.activities.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Espresso version of MainActivity tests.
 */
public class MainActivityTest2 extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mainActivity;
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
        Resources res = mainActivity.getResources();
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
        checkTeams(euLCS);
        checkTeams(lpl);
        checkTeams(lck);
        checkTeams(lms);
    }

    private void checkTeams(String[] teams) {
        for(String team : teams) {
            onData(allOf( is(instanceOf(String.class)), withTeamName(team), isDisplayed() ))
                    .inAdapterView(withId(android.R.id.list));
        }
    }

    public static Matcher<Object> withTeamName(String name) {
        return withTeamName(equalTo(name));
    }

    public static Matcher<Object> withTeamName(final Matcher<String> name) {
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
