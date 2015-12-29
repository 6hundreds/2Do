package com.six_hundreds.todo.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.six_hundreds.todo.fragment.CurrentTaskFragment;
import com.six_hundreds.todo.fragment.DoneTaskFragment;

/**
 * Created by six_hundreds on 26.12.15.
 */
public class TabAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public TabAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new CurrentTaskFragment();
            case 1 :
                return new DoneTaskFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
