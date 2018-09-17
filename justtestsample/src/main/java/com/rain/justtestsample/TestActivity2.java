package com.rain.justtestsample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Author:rain
 * Date:2018/9/14 17:13
 * Description:
 */
public class TestActivity2 extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG  ="TestActivity2";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        findViewById(R.id.btn_finish).setOnClickListener(this);
//        startTimer();
    }

    private void startTimer() {
        final int[] i = {0};
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
//                if (isFinishing()) {
//                    timer.cancel();
//                }
//                System.out.println("run: "+ i[0]++);
                System.out.println("run: ");
            }
        },0,2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_finish:
                finish();
                break;
        }
    }
}
