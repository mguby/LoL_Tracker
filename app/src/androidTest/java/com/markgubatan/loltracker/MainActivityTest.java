package com.markgubatan.loltracker;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.markgubatan.loltracker.ui.activities.MainActivity;

/**
 * Basic tests for MainActivity.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>{

    private MainActivity mainActivity;

    public MainActivityTest() {
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
}
