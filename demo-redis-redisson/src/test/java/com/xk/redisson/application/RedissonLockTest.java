package com.xk.redisson.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2022/4/12 23:10
 */
@SpringBootTest
public class RedissonLockTest {
    @Autowired
    private RedissonLock redissonLock;

    // 没获取到锁阻塞线程
    @Test
    public void test1() throws InterruptedException {
        new Thread(() -> redissonLock.test()).start();
        new Thread(() -> redissonLock.test()).start();
        new Thread(() -> redissonLock.test()).start();
        Thread.sleep(8000);
    }

    // 立刻返回获取锁的状态
    @Test
    public void test2() throws InterruptedException {
        new Thread(() -> redissonLock.test1()).start();
        new Thread(() -> redissonLock.test1()).start();
        new Thread(() -> redissonLock.test1()).start();
        Thread.sleep(8000);
    }

    // 立刻返回获取锁的状态，指定等待时间
    @Test
    public void test3() throws InterruptedException {
        new Thread(() -> redissonLock.test2()).start();
        new Thread(() -> redissonLock.test2()).start();
        new Thread(() -> redissonLock.test2()).start();
        Thread.sleep(8000);
    }
}
