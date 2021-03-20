package com.test.juc;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class MyLock {

    class Sync extends AbstractQueuedSynchronizer {

        protected boolean tryAcquire(int arg) {
            return compareAndSetState(0, 1);
        }

        protected boolean tryRelease(int arg) {
            setState(0);
            return true;
        }

        protected boolean isHeldExclusively() {
            // 主要是 condition用到, 随便意思一下
            return true;
        }
    }

    Sync sync = new Sync();

    public void lock() {
        sync.acquire(1);
    }

    public void unlock() {
        sync.release(1);
    }
}