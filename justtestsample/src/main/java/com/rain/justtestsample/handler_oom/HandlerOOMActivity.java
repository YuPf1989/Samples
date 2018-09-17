package com.rain.justtestsample.handler_oom;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.rain.justtestsample.R;

import java.lang.ref.WeakReference;

/**
 * Author:rain
 * Date:2018/9/17 15:28
 * Description:
 * 避免handle内存泄露的办法
 * 1.使用static 修饰的handler，但是一般会弱引用activity对象，因为要使用activity对象中的成员
 * 2.单独定义handler，同样可以弱引用activity
 * 3.使用内部类的handler，在onDestroy方法中removeCallbacksAndMessages
 */
public class HandlerOOMActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "HandlerOOMActivity";
    private TextView tv;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    private MyHandler handler = new MyHandler(this);

    private MyHandler3 myHandler3 = new MyHandler3();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handleroom);

        findViewById(R.id.btn_send).setOnClickListener(this);
        tv = findViewById(R.id.tv);

        // 延时5min发送一个消息，此时handler是持有activity引用的
        mHandler.sendEmptyMessageDelayed(1, 5 * 60 * 1000);
//        handler.sendEmptyMessageDelayed(0, 5 * 60 * 1000);
//        myHandler3.sendEmptyMessageDelayed(0, 1 * 60 * 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                new MyHandler(this).sendEmptyMessageDelayed(0, 5 * 60 * 1000);
                finish();
                break;
        }
    }

    /**
     * 避免handler内存泄露的写法
     * handler为何会导致内存泄露：
     * 如果handler为内部类（非静态内部类），那么会持有外部类的实例，
     * 若在handler.sendMessage的时候，activity finish掉了，那么此时activity将无法得到释放
     * <p>
     * 如果申明handler为静态内部类，则不会含有外部类的引用，
     * 但是需要在handler中更新UI（注意此时handler为static），则需要引入一个activity引用，
     * 显然必须是弱引用，否则会导致和上面一样的结果
     * <p>
     * 使用activity弱引用
     * 之所以使用弱引用，是因为handler为static,使用activity的弱引用来访问activity对象内的成员
     */
    private static class MyHandler extends Handler {
        private WeakReference<HandlerOOMActivity> weakReference;
//        private HandlerOOMActivity activity;

        public MyHandler(HandlerOOMActivity activity) {
            weakReference = new WeakReference<>(activity);
//            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e(TAG, "handleMessage: ");
//            HandlerOOMActivity activity = weakReference.get();
            switch (msg.what) {
                case 0:
//                    if (activity != null) {
//                        activity.tv.setText("我是更改后的文字");
//                    }
//                    activity.tv.setText("我是更改后的文字");
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
