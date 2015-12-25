package com.six_hundreds.todo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by six_hundreds on 24.12.15.
 */
public class PreferencesHelper {

    public static final String SHOW_SPLASH = "SHOW SPLASH";

    private static PreferencesHelper instance;
    private Context context;
    private SharedPreferences preferences;

    public PreferencesHelper() {
    }

    public static PreferencesHelper getInstance() {
        if (instance == null) {
            instance = new PreferencesHelper();
        }
        return instance;
    }

    public void init (Context context){
        this.context=context;
        preferences= context.getSharedPreferences("preferences",Context.MODE_PRIVATE);
    }

    public void putBoolean (String key, Boolean value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    public Boolean getBoolean (String key) {
        return preferences.getBoolean(key, false);
    }
}
