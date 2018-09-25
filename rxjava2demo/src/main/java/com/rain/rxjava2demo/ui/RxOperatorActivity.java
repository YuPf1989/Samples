package com.rain.rxjava2demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.rain.rxjava2demo.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Author:rain
 * Date:2018/9/21 10:30
 * Description:
 * create 最常见的操作符，用于生产一个发射对象
 * FlatMap将一个发送事件的上游Observable变换成多个发送事件的Observables
 * distinct 对发送的数据进行去重
 * Filter 过滤器
 * timer 延时器
 * interval 定时器
 */
public class RxOperatorActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RxOperatorActivity";
    private TextView tvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rxoperator);

        tvContent = findViewById(R.id.tv_content);
        findViewById(R.id.btn_create).setOnClickListener(this);
        findViewById(R.id.btn_map).setOnClickListener(this);
        findViewById(R.id.btn_flatmap).setOnClickListener(this);
        findViewById(R.id.btn_buffer).setOnClickListener(this);

    }

    private void clearText() {
        tvContent.setText("");
    }


    @Override
    public void onClick(View v) {
        clearText();
        switch (v.getId()) {
            case R.id.btn_create:
                rxCreate();
                break;

            case R.id.btn_map:
                rxMap();
                break;

            case R.id.btn_flatmap:
                rxflatMap();
                break;

            case R.id.btn_buffer:
                rxbuffer();
                break;


        }
    }

    /**
     * buffer(3,2) // count每次取几个值，skip每次取值后间隔几个值再取
     */
    private void rxbuffer() {
        Observable.just(1,2,3,4,5,6)
                .buffer(3,2) // count每次取几个值，skip每次取值后间隔几个值再取
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> list) throws Exception {
                        Log.e(TAG, "list.size(): "+list.size());
                        for (Integer i:list) {
                            Log.e(TAG, "rxbuffer: i:"+i);
                        }
                        Log.e(TAG, "------------------");
                    }
                });
    }

    /**
     * FlatMap将一个发送事件的上游Observable变换成多个发送事件的Observables
     */
    private void rxflatMap() {
        final ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Observable.create(new ObservableOnSubscribe<ArrayList>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList> emitter) throws Exception {
                emitter.onNext(list);
            }
        })
                .flatMap(new Function<ArrayList, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(ArrayList list) throws Exception {
                        Log.e(TAG, "apply: ");
                        List<String> list1 = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            list1.add("I'm value "+list.get(i));
                        }
                        Log.e(TAG, "flatMap: "+Thread.currentThread().getName());
                        return Observable.fromIterable(list1);
                    }

                })
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, "doOnNext: "+Thread.currentThread().getName());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, "subscribe: "+Thread.currentThread().getName());
                        tvContent.append(s);
                    }
                });
    }

    /**
     * 对上游发送的每一个事件应用一个函数，使得每一个事件都按照指定的函数去变化
     */
    private void rxMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        })
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        Log.e(TAG, "apply: ");
                        return integer + "";
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        tvContent.append(s);
                    }
                });
    }

    private void rxCreate() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        })
                .subscribe(new Observer<Integer>() {
                    Disposable mDisposable;
                    int i;

                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        i++;
                        // 切断订阅
                        if (i == 2) {
                            mDisposable.dispose();
                        }
                        tvContent.append(integer + "");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: ");
                    }
                });


    }
}
