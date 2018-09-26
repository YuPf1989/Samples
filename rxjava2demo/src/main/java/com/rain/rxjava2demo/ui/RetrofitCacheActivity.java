package com.rain.rxjava2demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.rain.commonlib.net.MyObserver.MyObserver;
import com.rain.commonlib.net.RetrofitHelper;
import com.rain.rxjava2demo.R;
import com.rain.rxjava2demo.api.RetrofitService;
import com.rain.rxjava2demo.bean.NewsListBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author:rain
 * Date:2018/9/26 15:16
 * Description:
 * 使用retrofitcache对get请求进行缓存，post是不需要缓存的
 */
public class RetrofitCacheActivity extends AppCompatActivity implements View.OnClickListener {
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
                break;
        }
    }

    /**
     * 使用retrofitcache进行缓存并请求
     */
    private void startCacheRequest() {
        clearContent();

        RetrofitHelper.getInstance().retrofit.create(RetrofitService.class).getWikiList2()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<NewsListBean>() {

                    @Override
                    public void onNext(NewsListBean data) {
                        String content = data.toString();
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
