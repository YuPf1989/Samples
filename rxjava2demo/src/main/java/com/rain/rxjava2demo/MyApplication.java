package com.rain.rxjava2demo;

import com.rain.commonlib.base.BaseApplication;

import java.util.concurrent.TimeUnit;

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
        // TODO: 2018/9/26 即使加上了这行代码 ，在有网络的情况下依旧请求网络
        RetrofitCache.getInstance().init(this).setDefaultTimeUnit(TimeUnit.MINUTES).setDefaultTime(1);
    }
}
