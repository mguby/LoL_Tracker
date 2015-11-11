package com.markgubatan.loltracker;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.markgubatan.loltracker.ui.activities.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Espresso version of MainActivity tests.
 */
public class MainActivityTest2 extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mainActivity;

    public MainActivityTest2() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mainActivity = getActivity();
    }

    @SmallTest
    public void testPreconditions() {
        assertNotNull("mainActivity was null", mainActivity);
    }

    @SmallTest
    public void testLeaguesFragment_displayed() {
        onView(withId(R.id.main_container)).check(matches(isDisplayed()));
    }

}
