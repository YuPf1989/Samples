package com.rain.rxjava2demo;

import com.rain.commonlib.base.BaseApplication;

import ren.yale.android.retrofitcachelibrx2.RetrofitCache;

/**
 * Author:rain
 * Date:2018/9/25 16:33
 * Description:
 */
public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitCache.getInstance().init(this);
    }
}
