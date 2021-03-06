package com.rain.commonlib.net;

import android.app.Application;

import com.rain.commonlib.base.BaseApplication;
import com.rain.commonlib.base.Constant;
import com.rain.commonlib.net.MyGsonConverter.MyGsonConverterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import ren.yale.android.retrofitcachelibrx2.RetrofitCache;
import ren.yale.android.retrofitcachelibrx2.intercept.CacheForceInterceptorNoNet;
import ren.yale.android.retrofitcachelibrx2.intercept.CacheInterceptorOnNet;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Author:rain
 * Date:2017/11/13 10:48
 * Description:retrofit工具类
 * 这里使用的是MyGsonConverterFactory.create()
 * 使用的是内层的数据
 * 主要用于测试rxcache
 */

public class RetrofitHelper {

    public static final int DEFAULT_TIMEOUT = 5;

    private static RetrofitHelper retrofitHelper;
    private static Application mContext;

    // 调用接口中的网络请求方法的对象
    public final Retrofit retrofit;

    public static RetrofitHelper getInstance() {
        if (retrofitHelper == null) {
            mContext = BaseApplication.getInstance();
            retrofitHelper = new RetrofitHelper();
        }
        return retrofitHelper;
    }

    private RetrofitHelper() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MyGsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();

        RetrofitCache.getInstance().addRetrofit(retrofit);
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        return builder.build();
    }

}
