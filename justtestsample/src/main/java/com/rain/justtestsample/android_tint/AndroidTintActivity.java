package com.rain.justtestsample.android_tint;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.rain.justtestsample.R;

/**
 * Author:rain
 * Date:2018/9/18 10:13
 * Description:
 * 研究安卓tint着色机制
 * 直接看布局文件
 */
public class AndroidTintActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tint);
    }


}
