package com.six_hundreds.todo;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
//import android.widget.Toolbar;


import com.six_hundreds.todo.adapter.TabAdapter;
import com.six_hundreds.todo.database.DBHelper;
import com.six_hundreds.todo.dialog.AddingDialogTaskFragment;
import com.six_hundreds.todo.dialog.EditTaskDialogFragment;
import com.six_hundreds.todo.fragment.CurrentTaskFragment;
import com.six_hundreds.todo.fragment.DoneTasksFragment;
import com.six_hundreds.todo.fragment.SplashFragment;
import com.six_hundreds.todo.fragment.TasksFragment;
import com.six_hundreds.todo.model.ModelTask;
import com.six_hundreds.todo.notification.AlarmHelper;

public class MainActivity extends AppCompatActivity
        implements AddingDialogTaskFragment.AddingTaskListener,
        CurrentTaskFragment.OnTaskDoneListener,
        DoneTasksFragment.OnTaskRestoreListener,
        EditTaskDialogFragment.EditingTaskListener{

    FragmentManager fragmentManager;
    PreferencesHelper preferencesHelper;

    TabAdapter tabAdapter;

    TasksFragment currentTaskFragment;
    TasksFragment doneTasksFragment;

    SearchView searchView;

    public DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(getApplicationContext());

        AlarmHelper.getInstance().init(getApplicationContext());

        PreferencesHelper.getInstance().init(getApplicationContext());
        preferencesHelper = PreferencesHelper.getInstance();
        fragmentManager = getFragmentManager();
        runSplash();

        setUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.splash_settings) {
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
        tabAdapter = new TabAdapter(fragmentManager, 2);

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

        currentTaskFragment = (CurrentTaskFragment) tabAdapter.getItem(TabAdapter.CURRENT_TASK_FRAGMENT_POSITION);
        doneTasksFragment = (DoneTasksFragment) tabAdapter.getItem(TabAdapter.DONE_TASK_FRAGMENT_POSITION);

        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentTaskFragment.findTasks(newText);
                doneTasksFragment.findTasks(newText);
                return false;
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
    protected void onResume() {
        super.onResume();
        MyApplication.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.activityPaused();
    }

    @Override
    public void onTaskAdded(ModelTask newTask) {
        currentTaskFragment.addTask(newTask, true);
        Toast.makeText(MainActivity.this, "Task added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTaskAddingCancel() {
        Toast.makeText(MainActivity.this, "Task adding cancelled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTaskDone(ModelTask task) {
        doneTasksFragment.addTask(task, false);
    }

    @Override
    public void onTaskRestore(ModelTask task) {
        currentTaskFragment.addTask(task, false);

    }

    @Override
    public void onTaskEdited(ModelTask updatedTask) {
        currentTaskFragment.updateTask(updatedTask);
        dbHelper.update().task(updatedTask);
    }
}
