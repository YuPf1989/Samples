package com.rain.justtestsample;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.rain.justtestsample.about_thread.TestActivity2;
import com.rain.justtestsample.android_tint.AndroidTintActivity;
import com.rain.justtestsample.handler_oom.HandlerOOMActivity;

/**
 * 此项目用于测试一些各种各样的问题
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG  = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_q1).setOnClickListener(this);
        findViewById(R.id.btn_q2).setOnClickListener(this);
        findViewById(R.id.btn_q3).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_q1:
                startActivity(new Intent(this,TestActivity2.class));
                break;

            case R.id.btn_q2:
                startActivity(new Intent(this,HandlerOOMActivity.class));
                break;

            case R.id.btn_q3:
                startActivity(new Intent(this,AndroidTintActivity.class));
                break;
        }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
