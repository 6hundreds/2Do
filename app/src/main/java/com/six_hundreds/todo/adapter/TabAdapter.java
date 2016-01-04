package com.six_hundreds.todo.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.six_hundreds.todo.fragment.CurrentTaskFragment;
import com.six_hundreds.todo.fragment.DoneTasksFragment;

/**
 * Created by six_hundreds on 26.12.15.
 */
public class TabAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public static final int CURRENT_TASK_FRAGMENT_POSITION = 0;
    public static final int DONE_TASK_FRAGMENT_POSITION = 1;

    private CurrentTaskFragment currentTaskFragment;
    private DoneTasksFragment doneTasksFragment;

    public TabAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        currentTaskFragment = new CurrentTaskFragment();
        doneTasksFragment = new DoneTasksFragment();
    }


    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return currentTaskFragment;
            case 1 :
                return doneTasksFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
