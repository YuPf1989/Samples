package com.rain.rxjava2demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.rain.commonlib.net.MyObserver.MyObserver;
import com.rain.commonlib.net.RetrofitHelper3;
import com.rain.commonlib.net.RetrofitHelper4;
import com.rain.rxjava2demo.R;
import com.rain.rxjava2demo.api.RetrofitService;
import com.rain.rxjava2demo.bean.BaseBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author:rain
 * Date:2018/9/26 15:16
 * Description:
 * 使用okhttp自带的cache对get请求进行缓存，post是不需要缓存的
 */
public class OkhttpCacheActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.rxcache_activity);
        tvContent = findViewById(R.id.tv_content);
        findViewById(R.id.btn_cache_request).setOnClickListener(this);
        findViewById(R.id.btn_cache_request2).setOnClickListener(this);
        findViewById(R.id.btn_cache_request3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cache_request:
                break;

            case R.id.btn_cache_request2:
                break;

            case R.id.btn_cache_request3:
                startCacheRequest();
                break;
        }
    }

    /**
     * 使用okhttpcache进行缓存并请求
     */
    private void startCacheRequest() {
        clearContent();

        RetrofitHelper3.getInstance().retrofit.create(RetrofitService.class).test2()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<BaseBean>() {

                    @Override
                    public void onNext(BaseBean data) {
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
