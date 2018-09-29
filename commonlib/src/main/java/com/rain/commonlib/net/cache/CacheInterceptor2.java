package com.rain.commonlib.net.cache;

import android.util.Log;

import com.rain.commonlib.base.BaseApplication;
import com.rain.commonlib.util.NetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author:rain
 * Date:2018/9/28 9:27
 * Description:
 * 该拦截器设置在RetrofitHelper4中，但不知道为何不打印，貌似也没有执行
 */
public class CacheInterceptor2 implements Interceptor {
    private static final String TAG = "CacheInterceptor2";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        // 没有网络，定义请求头里边的缓存策略
        if (!NetworkUtils.hasNetWorkConnection(BaseApplication.getInstance())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Log.e(TAG, "intercept: no-net");
        }

        Response response = chain.proceed(request);

        // 有网络定义响应头里的缓存策略
        if (NetworkUtils.hasNetWorkConnection(BaseApplication.getInstance())) {
            int maxAge = 60 ; // read from cache for 1 minute
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            // 没有网络，定义响应头里边的缓存策略
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();

            Log.e(TAG, "intercept: no-net-response");
        }

        int code = response.code();
        if (code == 504) {
            Log.e(TAG, "intercept: 缓存过期");
        }
        if (response.cacheResponse() != null) {
            Log.e(TAG, "intercept: from cache");
        }
        if (response.networkResponse() != null) {
            Log.e(TAG, "intercept: from net");
        }
        return response;
    }
}
