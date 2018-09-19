package com.rain.justtestsample.about_thread;

/**
 * Author:rain
 * Date:2018/9/17 9:52
 * Description:
 * sleep、wait的区别
 * sleep是Thread的方法，主要的意义就是让当前线程停止执行，让出cpu给其他的线程，但是不会释放对象锁资源以及监控的状态，当指定的时间到了之后又会自动恢复运行状态
 * wait是Object的方法,主要的意义就是让线程放弃当前的对象的锁，进入等待此对象的等待锁定池，只有针对此对象调动notify方法后本线程才能够进入对象锁定池准备获取对象锁进入运行状态
 * 其他说明：Thread2和Thread1持有的是同一个锁对象
 *
 * Thread run和start方法的区别
 * start：用start方法来启动线程，真正实现了多线程运行，这时无需等待run方法体代码执行完毕而直接继续执行下面的代码
 * run：run()方法只是类的一个普通方法而已，如果直接调用Run方法，程序中依然只有主线程这一个线程，其程序执行路径还是只有一条，还是要顺序执行
 *
 */
public class TestSleepAndWait {
    public static void main(String[] args) {
        // 如果将Thread1、Thread2的start改为run，由于顺序运行，Thread1将一直阻塞在这，他们都在主线程运行
        new Thread(new Thread1()).start();
        new Thread(new Thread2()).start();
//        new Thread(new Thread3()).start();
    }

    private static class Thread1 implements Runnable{

        @Override
        public void run() {
            synchronized (TestSleepAndWait.class) {
                System.out.println("enter thread1...");
                System.out.println("thread1 is waiting...");
                try {
                    // 调用wait()方法，线程会放弃对象锁，进入等待此对象的等待锁定池
                    TestSleepAndWait.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread1 is going on ....");
                System.out.println("thread1 is over!!!");
            }
        }
    }

    private static class Thread3 implements Runnable{

        @Override
        public void run() {
            synchronized (TestSleepAndWait.class) {
                System.out.println("enter thread3...");
                System.out.println("thread3 is sleep...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread3 is going on ....");
                System.out.println("thread3 is over!!!");
            }
        }
    }

    private static class Thread2 implements Runnable{
        @Override
        public void run(){
            synchronized (TestSleepAndWait.class) {
                System.out.println("enter thread2....");
                System.out.println("thread2 is sleep....");
                // 只有针对此对象调用notify()方法后本线程才进入对象锁定池准备获取对象锁进入运行状态。
                TestSleepAndWait.class.notify();
                //==================
                //区别
                //如果我们把代码：TestD.class.notify();给注释掉，即TestD.class调用了wait()方法，但是没有调用notify()
                //方法，则线程永远处于挂起状态。
                try {
                    //sleep()方法导致了程序暂停执行指定的时间，让出cpu该其他线程，
                    //但是他的监控状态依然保持者，当指定的时间到了又会自动恢复运行状态。
                    //在调用sleep()方法的过程中，线程不会释放对象锁。
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("thread2 is going on....");
                System.out.println("thread2 is over!!!");
            }
        }
    }
}
