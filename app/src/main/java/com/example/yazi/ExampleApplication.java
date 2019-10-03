package com.example.yazi;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class ExampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupLeakCAnnary();
    }

    protected void setupLeakCAnnary(){
        if (LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        LeakCanary.install(this);
    }
}
