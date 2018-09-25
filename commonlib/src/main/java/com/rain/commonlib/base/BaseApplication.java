package com.rain.commonlib.base;

import android.app.Application;

/**
 * Author:rain
 * Date:2018/9/25 16:22
 * Description:
 */
public class BaseApplication extends Application {
    private static BaseApplication application;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static Application getInstance() {
        return application;
    }
}
