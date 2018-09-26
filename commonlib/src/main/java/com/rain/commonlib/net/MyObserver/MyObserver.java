package com.rain.commonlib.net.MyObserver;


import com.rain.commonlib.base.BaseApplication;
import com.rain.commonlib.net.Exception.ApiErrorCode;
import com.rain.commonlib.net.Exception.ApiErrorHelper;
import com.rain.commonlib.net.Exception.ApiException;
import com.rain.commonlib.util.NetworkUtils;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Author:rain
 * Date:2017/11/13 14:20
 * Description:
 */

public abstract class MyObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        // retrofitcache 在没有网络的情况下走缓存，必须注释掉下边的代码
//        if (!NetworkUtils.hasNetWorkConnection(BaseApplication.getInstance())) {
//            this.onError(new ApiException(ApiErrorCode.NET_INTERRUPT,"没有网络连接！"));
//            d.dispose();
//        }
    }

    @Override
    public abstract void onNext(@NonNull T t);

    @Override
    public void onError(@NonNull Throwable e) {
        // 异常，统一交给该处理的类去处理
        ApiErrorHelper.handleCommonError(BaseApplication.getInstance(), e);
    }

    @Override
    public void onComplete() {

    }
}
