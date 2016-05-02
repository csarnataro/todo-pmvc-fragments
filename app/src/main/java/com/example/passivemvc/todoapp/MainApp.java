package com.example.passivemvc.todoapp;

import android.app.Application;
import android.content.Context;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * @author Christian Sarnataro
 *         Created on 29/04/16.
 */
public class MainApp extends Application {

    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initLibraries();
    }

    private void initLibraries() {
        FlowManager.init(new FlowConfig.Builder(context).build());
    }

    public static Context getAppContext() {
        return context;
    }

}
