package com.example.admin.annaporna;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Admin on 15-12-2016.
 */

public class SaiApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
