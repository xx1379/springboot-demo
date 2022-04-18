package com.xk.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2022/4/12 22:45
 */
@Configuration
public class RedissonConfig {
    /*
        springboot中可以入starter后直接配置文件配置
     */
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        // 单机模式
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        return Redisson.create(config);
    }
}
