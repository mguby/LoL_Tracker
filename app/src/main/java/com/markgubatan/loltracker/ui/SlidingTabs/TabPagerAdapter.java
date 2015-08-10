package com.markgubatan.loltracker.ui.SlidingTabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.markgubatan.loltracker.ui.fragments.TeamFragment;

/**
 * Created by Mark Gubatan on 6/23/2015.
 */
public class TabPagerAdapter extends FragmentStatePagerAdapter {
    CharSequence titles[];

    public TabPagerAdapter(FragmentManager fm, CharSequence TitleArray[]) {
        super(fm);
        this.titles = TitleArray;
    }

    @Override
    public Fragment getItem(int position) {
        TeamFragment fragment = new TeamFragment();
        Bundle bundle = new Bundle();
        switch(position) {
            // Send the league to retrieve to the fragment
            case 0:
                bundle.putString("league", "NALCS");
                break;

            case 1:
                bundle.putString("league", "EULCS");
                break;

            case 2:
                bundle.putString("league", "LCK");
                break;

            case 3:
                bundle.putString("league", "LPL");
                break;

            case 4:
                bundle.putString("league", "LMS");
                break;

        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
