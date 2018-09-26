package com.rain.rxjava2demo.api;

import com.rain.rxjava2demo.bean.NewsListBean;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import ren.yale.android.retrofitcachelibrx2.anno.Cache;
import retrofit2.http.GET;

/**
 * Author:rain
 * Date:2018/9/25 11:33
 * Description:
 */
public interface RetrofitService {
    /**
     * 百科列表
     */
    @GET("http://47.93.136.56:7011/appInterface/patient/getWikiList.html")
    Observable<NewsListBean> getWikiList();

    /**
     * 百科列表（带缓存,默认没有网络的时候强制走缓存）
     */
    @Cache(time = 2,timeUnit = TimeUnit.MINUTES,forceCacheNoNet = true)
    @GET("http://47.93.136.56:7011/appInterface/patient/getWikiList.html")
    Observable<NewsListBean> getWikiList2();

}
