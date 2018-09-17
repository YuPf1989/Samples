package com.rain.justtestsample;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Author:rain
 * Date:2018/9/14 18:00
 * Description:
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
