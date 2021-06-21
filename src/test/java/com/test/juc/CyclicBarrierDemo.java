package com.test.juc;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ryan
 * @since 2021/06/18
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) throws InterruptedException {
        normal();
    }

    private static void normal() throws InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(3, () -> {
            System.out.println("保险箱:恭喜主人,又拿到钱了");
        });
        if (!barrier.isBroken()) {
            System.out.println("保险箱:安全保护中");
        }
        System.out.println("亲信们:不成功便成仁!!!!!");
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new MyFollower(barrier, i + 1));
            thread.start();
            if (i == 0) {
                thread.interrupt();
            }
        }
        Thread.sleep(2000);
        if (barrier.isBroken()) {
            System.out.println("亲信:重置一下");
            barrier.reset();
        }

        if (!barrier.isBroken()) {
            System.out.println("保险箱:安全保护中");
            System.out.println("老板:很好,这东西不错");
        }
    }

    private static class MyFollower implements Runnable {
        private CyclicBarrier barrier;
        private int no;

        public MyFollower(CyclicBarrier barrier, int no) {
            this.barrier = barrier;
            this.no = no;
        }

        @Override
        public void run() {
            System.out.println("亲信:输入密码ing");
            if (no == 3) {
                System.out.println("亲信:输错了...重置一下");
                barrier.reset();
                return;
            }
            if (no == 2) {
                System.out.println("亲信:输慢了...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
            }
            try {
                barrier.await(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                System.out.println("临死前:骂骂咧咧地退出了游戏");
                return;
            } catch (BrokenBarrierException e) {
                System.out.println("OS:哪个傻子错了啊");
                return;
            } catch (TimeoutException e) {
                System.out.println("亲信:是谁输入慢了");
                return;
            }
            System.out.println("亲信:成了!!!!!");
        }
    }
}
