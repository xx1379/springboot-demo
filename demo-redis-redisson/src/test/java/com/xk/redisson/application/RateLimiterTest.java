package com.xk.redisson.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2022/4/12 23:10
 */
@SpringBootTest
public class RateLimiterTest {
    @Autowired
    private RateLimiter rateLimiter;

    @Test
    public void test1() throws InterruptedException {
        rateLimiter.init();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> rateLimiter.get()).start();
        }
        Thread.sleep(1000);
    }
}
