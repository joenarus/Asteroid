package com.example.joenarus.asteroids;

import android.app.Application;
import android.content.Context;

/**
 * Created by JoeNarus on 12/18/16.
 */
public class MyApplication extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }

    public static Context getContext() {
        return appContext;
    }
}
