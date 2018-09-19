package com.rain.justtestsample.about_thread;

/**
 * Author:rain
 * Date:2018/9/18 11:01
 * Description:
 * 测试多线程访问统一资源
 * 演示A、B两个线程交替输出
 *
 */
public class TestThreads {
    public static int i = 0;

    // 线程是否需要等待的标记位
    private static boolean islocked = false;

    public static void main(String[] args) {
        // 两个线程交替执行
//        new Thread(new Thread1()).start();
//        new Thread(new Thread2()).start();
        // 探究运行的究竟是哪个run方法
//        new MyThread(new Thread3()).start();
    }

    static class Thread1 implements Runnable {

        @Override
        public void run() {
            synchronized (TestThreads.class) {
                while (i < 10) {

                    if (islocked) {
                        try {
                            // 释放锁，同时进入锁定池
                            TestThreads.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();

                        }
                    } else {
                        islocked = true;
                        System.out.println(Thread.currentThread().getName());
                        i++;
                        System.out.println("i" + i);
                        TestThreads.class.notifyAll();
                    }
                }

            }
        }
    }

    static class Thread2 implements Runnable {

        @Override
        public void run() {
            synchronized (TestThreads.class) {
                while (i < 10) {

                    if (!islocked) {
                        try {
                            // 释放锁，同时进入锁定池
                            TestThreads.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        islocked = false;
                        System.out.println(Thread.currentThread().getName());
                        i++;
                        System.out.println("i" + i);
                        TestThreads.class.notifyAll();
                    }

                }

            }
        }
    }

    static class MyThread extends Thread {
        public MyThread(Runnable target) {
            super(target);
        }

        @Override
        public void run() {
            // 可以查看源码，先调用的是传进来的Runable接口中的run方法，其次是Thread中的run方法
            // 其实Thread中的run方法是实现了Runable接口中的run方法
            super.run();
            System.out.println("MyThread");
        }
    }
}
