package com.test.juc;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;
public class ReadWriteLockDemo {
    private Integer cache;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public Integer getFromCache() {
        lock.readLock().lock();
        System.out.println(Thread.currentThread().getName() + ":开始读取缓存");
        Integer res = cache;
        System.out.println(Thread.currentThread().getName() + ":结束读取缓存");
        lock.readLock().unlock();
        if (cache == null) {
            System.out.println(Thread.currentThread().getName() + ":缓存不存在");
            return loadCache();
        }
        return cache;
    }

    private Integer loadCache() {
        lock.writeLock().lock();
        Integer val;
        if (cache == null) {
            System.out.println(Thread.currentThread().getName() + ":开始加载数据库");
            // get from database...
            _load();
            val = new Random(5).nextInt();
            cache = val;
            System.out.println(Thread.currentThread().getName() + ":结束加载数据库");
            lock.writeLock().unlock();
        } else {
            lock.readLock().lock();
            lock.writeLock().unlock();
            System.out.println(Thread.currentThread().getName() + ":开始读取缓存");
            val = cache;
            System.out.println(Thread.currentThread().getName() + ":结束读取缓存");
            lock.readLock().unlock();
        }
        return val;
    }

    private void _load() {
        // 模拟读写数据库的耗时
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setCache(Integer val) {
        lock.writeLock().lock();
        System.out.println(Thread.currentThread().getName() + ":开始写缓存");
        // write to database...
        _load();
        cache = val;
        System.out.println(Thread.currentThread().getName() + ":结束写缓存");
        lock.writeLock().unlock();
    }

    public static void main(String[] args) {
        ReadWriteLockDemo cache = new ReadWriteLockDemo();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(() -> cache.getFromCache());
        executorService.execute(() -> cache.getFromCache());
        executorService.execute(() -> cache.getFromCache());
        executorService.execute(() -> cache.getFromCache());
        executorService.execute(() -> cache.getFromCache());
    }
}