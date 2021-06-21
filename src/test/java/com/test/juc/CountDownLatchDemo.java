package com.test.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ryan
 * @since 2021/06/18
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        rr();
        countDown();
        cycleCountDown();
    }

    private static void rr() throws InterruptedException {
        System.out.println("老板:我要开锁");
        AtomicInteger count = new AtomicInteger(3);
        for (int i = 0; i < count.get(); i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    count.decrementAndGet();
                }
            }).start();
        }
        while(count.get() != 0) {
            System.out.println("老板:几个傻子给我快点");
            Thread.sleep(1000);
        }
        System.out.println("老板:拿钱跑路, 亲信卖了");
    }

    private static void countDown() {
        System.out.println("老板:我要开锁");
        CountDownLatch latch = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            new Thread(new Follower(latch)).start();
        }
        try {
            latch.await(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.out.println("老板:都是傻子吗,这都输入失败了");
        }
        System.out.println("老板:拿钱跑路, 亲信卖了");
    }
    private static class Follower implements Runnable {
        private CountDownLatch latch;

        public Follower(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {

            System.out.println("亲信:输入密码ing");
            latch.countDown();
            System.out.println("亲信:报告老板, 输入完成.");
        }
    }

    private static void cycleCountDown() throws InterruptedException {
        System.out.println("亲信们:不成功便成仁!!!!!");
        CountDownLatch latch = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            new Thread(new MyFollower(latch)).start();
        }
        Thread.sleep(1000);
    }

    private static class MyFollower implements Runnable {
        private CountDownLatch latch;

        public MyFollower(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            System.out.println("亲信:输入密码ing");
            latch.countDown();
            System.out.println("亲信:我这边搞定了,等你们.");
            try {
                latch.await(1, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                System.out.println("OS:你们还想不想要钱了");
            }

            System.out.println("亲信:成了!!!!!");
        }
    }
}
