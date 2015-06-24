package com.markgubatan.loltracker.ui.SlidingTabs;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

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
        switch(position) {

            case 0:
                //LessonListFragment lesson = new LessonListFragment();
                //MainFragment lesson = new MainFragment();
                //return lesson;

            case 1:
                //QuizListFragment quiz = new QuizListFragment();
                //SearchActivityFragment quiz = new SearchActivityFragment();
                //return quiz;

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
