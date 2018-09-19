package com.rain.justtestsample.about_thread;

/**
 * Author:rain
 * Date:2018/9/18 12:12
 * Description:
 */
public class MyThread extends BaseThread implements MyRunable{
    private MyRunable target;
    public MyThread(MyRunable target) {
        this.target = target;
    }

    @Override
    public void run() {
        super.run();
    }
}
