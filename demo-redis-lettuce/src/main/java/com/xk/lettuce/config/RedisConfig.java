package com.xk.lettuce.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2022/3/14 22:55
 * 接口里的RedisTemplate第一个Object使用时需要强转，很多时候我们不会使用默认的方法，而是自己定义一个
 */
@Configuration
public class RedisConfig {
    // 自己定义了一个RedisTemplate
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        /*
            spring-data-redis的RedisTemplate模板类在操作redis时默认使用JdkSerializationRedisSerializer进行序列化，
            并在Redis中存储序列化后的二进制字节。占用存储空间小，但可读性差
         */
        // JSON序列化配置
        Jackson2JsonRedisSerializer<?> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        jsonRedisSerializer.setObjectMapper(objectMapper);

        // String的序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // value采用jackson序列化方式
        template.setValueSerializer(jsonRedisSerializer);
        // hash的key采用String序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // hash的value采用jackson序列化方式
        template.setHashValueSerializer(jsonRedisSerializer);

        // 支持事务
//        template.setEnableTransactionSupport(true);

        template.afterPropertiesSet();

        return template;
    }

    /**
     * 自定义lettuce连接工厂，此处仅作示意，不需要自己创建
     *
     * @return
     */
//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory() {
//
//        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379));
//    }
}
