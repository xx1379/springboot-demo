package com.xk.redisson.application;

import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2022/4/12 23:25
 */
@Component
public class RateLimiter {
    @Autowired
    private RedissonClient redissonClient;

    //初始化限流器
    public void init() {
        RRateLimiter limiter = redissonClient.getRateLimiter("rateLimiter");
        limiter.trySetRate(RateType.PER_CLIENT, 5, 1, RateIntervalUnit.SECONDS);//每1秒产生5个令牌
    }

    //获取令牌
    public void get() {
        RRateLimiter limiter = redissonClient.getRateLimiter("rateLimiter");
        if (limiter.tryAcquire()) {//尝试获取1个令牌
            System.out.println(Thread.currentThread().getName() + "成功获取到令牌");
        } else {
            System.out.println(Thread.currentThread().getName() + "未获取到令牌");
        }
    }
}
