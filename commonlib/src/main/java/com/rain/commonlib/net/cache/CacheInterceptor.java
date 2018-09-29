package com.rain.commonlib.net.cache;

import android.text.TextUtils;
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
 * 注意该拦截器是添加到了网络拦截器中，只有走网络流该拦截器才会发生作用
 * 如果服务器不支持缓存就可能没有指定这个头部，这种情况下我们就需要使用Interceptor来重写Respose的头部信息，
 * 从而让okhttp支持缓存
 * 不管有没有网络都先读取缓存，缓存时间60s,
 * okhttp内部处理逻辑应该是这样:
 * 1.有缓存且缓存不过期，直接走缓存
 * 2.没有缓存、缓存过期，请求网络并更新缓存并缓存60s
 */
public class CacheInterceptor implements Interceptor {
    private static final String TAG = "CacheInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String cacheControl = request.cacheControl().toString();
        if (TextUtils.isEmpty(cacheControl)) {
            // 缓存时间为60s
            cacheControl = "public, max-age=60";
        }

        return response.newBuilder()
                .removeHeader("Cache-Control")
                .header("Cache-Control", cacheControl)
                .removeHeader("Pragma")
                .build();
    }
}
