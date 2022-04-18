package com.xk.redisson.application;

import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2022/4/14 23:22
 */
@SpringBootTest
public class ReentrantLockTest {
    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void test() {
        RLock lock = redissonClient.getLock("11");
        lock.lock();
        lock.tryLock(10, 5, TimeUnit.DAYS);
        lock.unlock();
    }

}
