package com.rain.commonlib.net;

import android.app.Application;

import com.rain.commonlib.base.BaseApplication;
import com.rain.commonlib.base.Constant;
import com.rain.commonlib.net.cache.CacheForceNoNetInterceptor;
import com.rain.commonlib.net.cache.CacheInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import ren.yale.android.retrofitcachelibrx2.RetrofitCache;
import ren.yale.android.retrofitcachelibrx2.intercept.CacheForceInterceptorNoNet;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author:rain
 * Date:2017/11/13 10:48
 * Description:retrofit工具类
 * okhttpcache 使用的retrofit
 */

public class RetrofitHelper3 {

    public static final int DEFAULT_TIMEOUT = 5;

    private static RetrofitHelper3 retrofitHelper;
    private static Application mContext;

    // 调用接口中的网络请求方法的对象
    public final Retrofit retrofit;

    public static RetrofitHelper3 getInstance() {
        if (retrofitHelper == null) {
            mContext = BaseApplication.getInstance();
            retrofitHelper = new RetrofitHelper3();
        }
        return retrofitHelper;
    }

    private RetrofitHelper3() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();

        RetrofitCache.getInstance().addRetrofit(retrofit);
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 200M
        int cacheSize = 200 * 1024 * 1024;
        File httpcacheFile = new File(mContext.getCacheDir(), "okhttpcache");
        Cache cache = new Cache(httpcacheFile, cacheSize);
        builder
                .cache(cache)
                // addInterceptor添加的是应用拦截器，先与网络拦截器调用,与是否有网络无关
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                .addInterceptor(new CacheForceInterceptorNoNet())
                .addInterceptor(new CacheForceNoNetInterceptor())
                // 在断网的情况下，会出网络连接异常
                .addNetworkInterceptor(new CacheInterceptor())
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        return builder.build();
    }
}
