package com.six_hundreds.todo;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
//import android.widget.Toolbar;


import com.six_hundreds.todo.adapter.TabAdapter;
import com.six_hundreds.todo.dialog.AddingDialogTaskFragment;
import com.six_hundreds.todo.fragment.SplashFragment;

public class MainActivity extends AppCompatActivity implements AddingDialogTaskFragment.AddingTaskListener{

    FragmentManager fragmentManager;
    PreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferencesHelper.getInstance().init(getApplicationContext());
        preferencesHelper = PreferencesHelper.getInstance();
        fragmentManager = getFragmentManager();
        runSplash();

        setUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =item.getItemId();

        if (id == R.id.splash_settings){
            item.setChecked(!item.isChecked());
            preferencesHelper.putBoolean(PreferencesHelper.SHOW_SPLASH, item.isChecked());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem splashItem = menu.findItem(R.id.splash_settings);
        splashItem.setChecked(preferencesHelper.getBoolean(PreferencesHelper.SHOW_SPLASH));
        return true;

    }

    public void runSplash() {

        if (preferencesHelper.getBoolean(PreferencesHelper.SHOW_SPLASH)) {
            SplashFragment splashFragment = new SplashFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, splashFragment)
                    .addToBackStack(null)
                    .commit();
        }

    }

    private void setUI() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.text_white));
            setSupportActionBar(toolbar);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.current_task_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.done_task_tab));

        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabAdapter tabAdapter = new TabAdapter(fragmentManager,2);

        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment addingTaskDialogFragment = new AddingDialogTaskFragment();
                addingTaskDialogFragment.show(fragmentManager, "AddingTaskDialogFragment");
            }
        });

    }


    @Override
    public void onTaskAdded() {
        Toast.makeText(MainActivity.this, "Task added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTaskAddingCancel() {
        Toast.makeText(MainActivity.this, "Task adding cancelled", Toast.LENGTH_SHORT).show();
    }
}
