package com.six_hundreds.todo;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.six_hundreds.todo.fragment.SplashFragment;

public class MainActivity extends AppCompatActivity {

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
            toolbar.setTitleTextColor(getResources().getColor(R.color.text_white, null));
            setActionBar(toolbar);
        }

    }
}
