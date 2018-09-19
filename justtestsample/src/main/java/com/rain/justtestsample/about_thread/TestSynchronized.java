package com.rain.justtestsample.about_thread;

/**
 * Author:rain
 * Date:2018/9/19 10:29
 * Description:
 * 测试同步方法的同步性并进行验证
 * 预期结果：无论哪一个线程先执行，由于同步锁的机制，另一个线程的输出都会进行等待，直到上一个同步块执行完毕
 * 分析：如果没有同步锁，那么一个线程执行遇到了阻塞不应该影响到另一个线程
 * 可以通过注释掉线程阻塞代码分别进行观察
 */
public class TestSynchronized {
    public static void main(String[] args) {
        ClassA classA = new ClassA();
        new Thread(new Thread1(classA)).start();
        new Thread(new Thread2(classA)).start();
    }

    static class ClassA {
        public synchronized void method1() {
            System.out.println("method1");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }

        public void method2() {
            synchronized (this) {
                System.out.println("method2");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }
    }

    static class Thread1 implements Runnable {
        private ClassA classA;


        public Thread1(ClassA classA) {
            this.classA = classA;
        }

        @Override
        public void run() {
            classA.method1();

        }
    }

    static class Thread2 implements Runnable {
        private ClassA classA;


        public Thread2(ClassA classA) {
            this.classA = classA;
        }

        @Override
        public void run() {
            classA.method2();

        }
    }


}
