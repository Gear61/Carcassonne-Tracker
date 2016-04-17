package com.randomappsinc.carcassonnetracker;

import android.app.Application;
import android.content.Context;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by alexanderchiou on 4/16/16.
 */
public final class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Iconify.with(new FontAwesomeModule());
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }
}
