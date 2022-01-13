package com.xk.redis;

import com.xk.redis.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2022/1/13 22:52
 */
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis1() {
        /**
         * 一般实际开发中，不会使用这个原生的方式去编写代码
         * opsForValue 操作字符串 类似String
         * opsForList  操作列表  类似List
         * opsForSet
         * opsForZSet
         * opsForHash
         * opsForGeo
         * opsForHyperLogLog
         *
         * 除了基本的操作，常用的方法都可以直接用redisTemplate操作，比如事务等
         */
        redisTemplate.opsForValue().set("mykey", "myvalue");
        System.out.println(redisTemplate.opsForValue().get("mykey"));


        /**
         * 获取redis的连接对象
         */
//        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
    }

    @Test
    public void testRedis2() {
        User user = new User("小王", 28);
        redisTemplate.opsForValue().set("user", user);
        System.out.println(redisTemplate.opsForValue().get("user"));
    }
}
