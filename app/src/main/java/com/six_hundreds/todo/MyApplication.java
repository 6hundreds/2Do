package com.six_hundreds.todo;

import android.app.Application;
import android.content.Context;

/**
 * Created by six_hundreds on 06.01.16.
 */
public class MyApplication extends Application {


    private static boolean activityVisible;

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed () {
        activityVisible = true;
    }

    public static void activityPaused(){
        activityVisible = false;
    }

}
