package com.markgubatan.loltracker.ui.SlidingTabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.markgubatan.loltracker.ui.fragments.TeamFragment;

/**
 * Tab selector that chooses which league to display
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
        bundle.putString("league", (String)titles[position]);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
