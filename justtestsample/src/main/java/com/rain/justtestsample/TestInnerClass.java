package com.rain.justtestsample;

/**
 * Author:rain
 * Date:2018/9/17 12:05
 * Description:
 * 各种内部类的创建和调用
 */
public class TestInnerClass {
    public static void main(String[] args) {
        // 初始化bean1
        TestInnerClass innerClass = new TestInnerClass();
        Bean1 bean1 = innerClass.new Bean1();
        System.out.println(bean1.I);
        // 初始化bean2
        Bean2 bean2 = new TestInnerClass.Bean2();
        System.out.println(bean2.J);
        // 初始化bean3
        Bean3 bean3 = new Bean3();
        Bean3.Bean33 bean33 = bean3.new Bean33();
        System.out.println(bean33.K);
    }

    class Bean1 {
        public int I = 0;
    }

    static class Bean2 {
        public int J = 1;
    }
}
