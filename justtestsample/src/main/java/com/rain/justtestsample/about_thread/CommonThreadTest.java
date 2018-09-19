package com.rain.justtestsample.about_thread;

/**
 * Author:rain
 * Date:2018/9/18 15:38
 * Description:
 * <p>
 * Thread常用函数说明
 * ①sleep(long millis): 在指定的毫秒数内让当前正在执行的线程休眠（暂停执行）
 * ②join():指等待t线程终止。
 * ③yield():暂停当前正在执行的线程对象，并执行其他线程。
 * sleep()和yield()的区别):sleep()使当前线程进入停滞状态，所以执行sleep()的线程在指定的时间内肯定不会被执行；
 * yield()只是使当前线程重新回到可执行状态，所以执行yield()的线程有可能在进入到可执行状态后马上又被执行。
 * 另外，sleep 方法允许较低优先级的线程获得运行机会，但 yield()  方法执行时，当前线程仍处在可运行状态，所以，不可能让出较低优先级的线程些时获得 CPU 占有权
 * ⑤interrupt():不要以为它是中断某个线程！它只是线线程发送一个中断信号，让线程在无限等待时（如死锁时）能抛出抛出，
 * 从而结束线程，但是如果你吃掉了这个异常，那么这个线程还是不会中断的！
 * ⑥wait() Obj.wait()，与Obj.notify()必须要与synchronized(Obj)一起使用，也就是wait,与notify是针对已经获取了Obj锁进行操作，
 * 从语法角度来说就是Obj.wait(),Obj.notify必须在synchronized(Obj){...}语句块内。
 * 从功能上来说wait就是说线程在获取对象锁后，主动释放对象锁，同时本线程休眠。
 * ⑦Interrupt 函数，如果线程A希望立即结束线程B，则可以对线程B对应的Thread实例调用interrupt方法。如果此刻线程B正在wait/sleep /join，
 * 则线程B会立刻抛出InterruptedException，在catch() {} 中直接return即可安全地结束线程
 *
 */
public class CommonThreadTest {
    public static void main(String[] args) {
        // join函数使用
//        Thread thread = new Thread(new Thread1());
//        thread.start();
//        try {
//            thread.join();
//            System.out.println("main");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // yield函数使用
//        Thread ty = new Thread(new YieldThread("thread0..."));
//        Thread ty2 = new Thread(new YieldThread("thread1..."));
//        // 设置线程优先级，取值1-10，值越大，优先级越高，cpu调度运行概率越大
//        ty.setPriority(Thread.NORM_PRIORITY);
//        ty2.setPriority(Thread.MAX_PRIORITY);
//        ty.start();
//        ty2.start();

        // wait函数使用
        new Thread(new Thread2("Thread2...")).start();
        new Thread(new Thread1()).start();
    }

    static class Thread1 implements Runnable {
        @Override
        public void run() {
            synchronized (CommonThreadTest.class) {
                try {
                    Thread.sleep(2000);
                    System.out.println("Thread1 start");
                    CommonThreadTest.class.notifyAll();
                    Thread.sleep(2000);
                    System.out.println("Thread1 over");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Thread2 implements Runnable {
        private String name;
        public Thread2(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            synchronized (CommonThreadTest.class) {
                for (int i = 0; i < 10; i++) {
                    if (i == 5) {
                        try {
                            CommonThreadTest.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(name+i);
                }
            }
        }
    }

    static class Thread3 implements Runnable {
        private String name;
        public Thread3(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            synchronized (CommonThreadTest.class) {
                for (int i = 0; i < 10; i++) {
                    if (i == 5) {
                        try {
                            CommonThreadTest.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(name+i);
                }
            }
        }
    }

    static class YieldThread implements Runnable {
        private String name;

        public YieldThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(name + "start");
            for (int i = 0; i < 10; i++) {
                if (i == 5) {
                    Thread.yield();
                    System.out.println(name + i);
                }
            }

            System.out.println(name + "over");
        }
    }


}
