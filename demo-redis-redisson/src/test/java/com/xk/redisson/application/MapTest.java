package com.xk.redisson.application;

import org.junit.jupiter.api.Test;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.event.EntryCreatedListener;
import org.redisson.api.map.event.EntryExpiredListener;
import org.redisson.api.map.event.EntryRemovedListener;
import org.redisson.api.map.event.EntryUpdatedListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2022/4/13 21:53
 */

@SpringBootTest
public class MapTest {
    @Autowired
    private RedissonClient redisson;

    /**
     * map针对单个元素的淘汰机制
     */
    @Test
    public void testRMapCache() throws InterruptedException {
        RMapCache<String, String> map = redisson.getMapCache("myMap");
        // 有效时间 ttl = 10秒钟
        map.put("k1", "v1", 10, TimeUnit.SECONDS);
        // 有效时间 ttl = 10分钟, 最长闲置时间 maxIdleTime = 10秒钟
        map.put("k2", "v2", 10, TimeUnit.MINUTES, 10, TimeUnit.SECONDS);
        Thread.sleep(5000);
        System.out.println(map.get("k2")); // v2
        Thread.sleep(6000);
        System.out.println(map.get("k1"));  // null
        System.out.println(map.get("k2"));  // v2
        Thread.sleep(10000);
        System.out.println(map.get("k2"));  // null
    }

    /**
     * map监听器
     */
    @Test
    public void testMapListener() throws InterruptedException {
        RMapCache<Integer, Integer> map = redisson.getMapCache("myMapListener");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        // 元素添加事件
        int createListener = map.addListener((EntryCreatedListener<Integer, Integer>) event -> {
            Integer key = event.getKey();// 字段名
            Integer value = event.getValue();// 值
            System.out.println(String.format("time=%s, create=%s", LocalDateTime.now().format(formatter), key));
        });
        // 元素更新事件
        int updateListener = map.addListener((EntryUpdatedListener<Integer, Integer>) event -> {
            Integer key = event.getKey();// 字段名
            Integer value = event.getValue();// 新值
            Integer oldValue = event.getOldValue();// 旧值
            System.out.println(String.format("time=%s, update=%s", LocalDateTime.now().format(formatter), key));
        });
        // 元素过期事件
        int expireListener = map.addListener((EntryExpiredListener<Integer, Integer>) event -> {
            Integer key = event.getKey();// 字段名
            Integer value = event.getValue();// 值
            System.out.println(String.format("time=%s, expire=%s", LocalDateTime.now().format(formatter), key));
        });
        // 元素删除事件
        int removeListener = map.addListener((EntryRemovedListener<Integer, Integer>) event -> {
            Integer key = event.getKey();// 字段名
            Integer value = event.getValue();// 值
            System.out.println(String.format("time=%s, remove=%s", LocalDateTime.now().format(formatter), key));
        });
        map.put(1, 1, 5, TimeUnit.SECONDS);
        Thread.sleep(1000);
        map.put(2, 2, 10, TimeUnit.MINUTES);
        Thread.sleep(1000);
        map.put(2, 3, 10, TimeUnit.MINUTES);
        Thread.sleep(1000);
        map.remove(2);
        Thread.sleep(4000);
        map.removeListener(updateListener);
        map.removeListener(createListener);
        map.removeListener(expireListener);
        map.removeListener(removeListener);
    }

    /**
     * LRU有界映射
     */
    @Test
    public void testLRU() {
        RMapCache<String, String> map = redisson.getMapCache("LRUMap");
        // 尝试将该映射的最大容量限制设定为10
        map.trySetMaxSize(2);
        // 将该映射的最大容量限制设定或更改为10
        map.setMaxSize(2);
        map.put("1", "2");
        map.put("2", "2");
        map.get("2");
        map.put("3", "3");
        System.out.println(map.get("1"));  // null
        System.out.println(map.get("2"));  // 2
        System.out.println(map.get("3"));  // 3
    }
}
