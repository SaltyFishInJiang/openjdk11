package com.test.juc;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author ryan
 * @since 2021/03/20
 */
public class AqsDemo {
    static int lockCount = 0;
    static int unlockCount = 0;
    static MyLock myLock = new MyLock();

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run () {
                try {
                    for (int i = 0; i < 10000; i++) {
                        unlockCount++;
                    }
                    // 睡一会,确保会有线程切换
                    Thread.sleep(1000);
                    myLock.lock();
                    System.out.println(Thread.currentThread().getName() + ":我开始跑了");
                    for (int i = 0; i < 10000; i++) {
                        lockCount++;
                    }
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + ":我跑完了");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    myLock.unlock();
                }

            }
        };
        Thread thread1 = new Thread(runnable, "线程1");
        Thread thread2 = new Thread(runnable, "线程2");
        thread1.start();thread2.start();thread1.join();thread2.join();
        System.out.println("加锁累加: " + lockCount);
        System.out.println("未加锁累加: " + unlockCount);
    }
}
