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
 * Date:2018/9/28 11:12
 * Description:
 * 实际发现写这个没什么用
 * 在没有网络情况下，强制使用缓存
 * 一旦缓存超时，没有网络的情况下，同样不会走缓存
 */
public class CacheForceNoNetInterceptor implements Interceptor {
    private static final String TAG = "CacheForce";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkUtils.hasNetWorkConnection(BaseApplication.getInstance())) {
            request = request.newBuilder()
                    .removeHeader("Pragma")
                    .cacheControl(CacheControl.FORCE_CACHE)
                    // max-stale和max-age区别在于一旦缓存过期将发起新的请求，请求失败将返回失败
                    // max-age请求失败将返回缓存
                    // max-stale 一般都是用于请求头，max-age一般用于响应头
//                    .header("Cache-Control", "only-if-cached, max-stale=" + 60)
                    .build();
            Log.e(TAG, "intercept: no-net");
        }
        Response response = chain.proceed(request);
        int code = response.code();
        // 504表示缓存过期不可用
        // 在没有网络，并且缓存过期,或者是没有缓存情况下出现
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
