package com.rain.rxjava2demo.api;

import com.rain.rxjava2demo.bean.NewsListBean;

import io.reactivex.Observable;
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

}
