package com.rain.rxjava2demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.rain.rxjava2demo.R;

/**
 * Author:rain
 * Date:2018/9/21 10:32
 * Description:
 */
public class RxDemoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rxdemo);
        findViewById(R.id.btn_rxcache).setOnClickListener(this);
        findViewById(R.id.btn_retrofit_cache).setOnClickListener(this);
        tvContent = findViewById(R.id.tv_content);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rxcache:
                startActivity(new Intent(this, RxCacheActivity.class));
                break;

            case R.id.btn_retrofit_cache:
                startActivity(new Intent(this, RetrofitCacheActivity.class));
                break;
        }
    }


}
