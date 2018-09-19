package com.rain.justtestsample.about_thread;

/**
 * Author:rain
 * Date:2018/9/18 14:35
 * Description:
 */
public  class BaseThread implements MyRunable{
    private MyRunable tartget;

    public BaseThread() {
    }

    public BaseThread(MyRunable target) {
        this.tartget = target;
    }

    @Override
    public void run(){
        if (tartget != null) {
            tartget.run();
        }
    }
}
