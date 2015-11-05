package com.markgubatan.loltracker.ui.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.markgubatan.loltracker.R;
import com.markgubatan.loltracker.ui.SlidingTabs.SlidingTabLayout;
import com.markgubatan.loltracker.ui.SlidingTabs.TabPagerAdapter;
import com.markgubatan.loltracker.ui.activities.MainActivity;


public class LeaguesFragment extends Fragment {
    private ViewPager viewPager;
    private TabPagerAdapter adapter;
    private SlidingTabLayout tabs;
    private MainActivity myContext;
    private final static CharSequence titleArray[] = {"NALCS", "EULCS", "LCK", "LPL", "LMS"};

    public static LeaguesFragment newInstance() {
        return new LeaguesFragment();
    }

    public LeaguesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_leagues, container, false);
        viewPager = (ViewPager)rootView.findViewById(R.id.viewpager);
        adapter = new TabPagerAdapter(getChildFragmentManager(), titleArray);
        viewPager.setAdapter(adapter);
        tabs = (SlidingTabLayout)rootView.findViewById(R.id.sliding_tabs);
        tabs.setDistributeEvenly(true);
        tabs.setViewPager(viewPager);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Activity activity) {
        myContext = (MainActivity) activity;
        super.onAttach(activity);
    }


}
