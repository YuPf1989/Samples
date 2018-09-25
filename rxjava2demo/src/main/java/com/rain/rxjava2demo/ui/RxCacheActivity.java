package com.rain.rxjava2demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.rain.commonlib.net.MyObserver.MyObserver;
import com.rain.commonlib.net.RetrofitHelper;
import com.rain.rxjava2demo.R;
import com.rain.rxjava2demo.api.CacheProviders;
import com.rain.rxjava2demo.api.RetrofitService;
import com.rain.rxjava2demo.bean.NewsListBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;

/**
 * Author:rain
 * Date:2018/9/25 11:45
 * Description:
 */
public class RxCacheActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.rxcache_activity);
        tvContent = findViewById(R.id.tv_content);
        findViewById(R.id.btn_cache_request).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cache_request:
                startCacheRequest();
        }
    }

    /**
     * 使用rxcache进行缓存并请求
     */
    private void startCacheRequest() {
        clearContent();

        Observable<NewsListBean> wikiList = RetrofitHelper.getInstance().retrofit.create(RetrofitService.class).getWikiList();
        CacheProviders using = new RxCache.Builder()
                // 在请求出错的情况下，忽视缓存过期的情况，依旧使用缓存，前提是缓存存在
                .useExpiredDataIfLoaderNotAvailable(true)
                .persistence(getCacheDir(), new GsonSpeaker())
                .using(CacheProviders.class);

        // DynamicKey:请求同一个接口时，参数不同返回的数据不同，比如分页 {@link CacheProviders}
        // EvictDynamicKey:是否不使用缓存
        using.getNewsList2(wikiList, new DynamicKey(1), new EvictDynamicKey(false))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<Reply<NewsListBean>>() {

                    // 注意rxcache对返回值进行了包装
                    @Override
                    public void onNext(Reply<NewsListBean> reply) {
                        String content = reply.getData().toString();
                        String source = reply.getSource().name();
                        tvContent.append("source:" + source + "\n");
                        tvContent.append("content:" + content + "\n");
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    private void clearContent() {
        tvContent.setText("");
    }
}
