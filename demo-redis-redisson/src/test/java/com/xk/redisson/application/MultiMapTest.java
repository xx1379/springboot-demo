package com.xk.redisson.application;

import org.junit.jupiter.api.Test;
import org.redisson.api.RListMultimap;
import org.redisson.api.RSetMultimap;
import org.redisson.api.RSetMultimapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2022/4/13 22:48
 */
@SpringBootTest
public class MultiMapTest {
    @Autowired
    private RedissonClient redisson;

    /**
     * 基于Set的Multimap不允许一个字段值包含有重复的元素。
     */
    @Test
    public void testSetMultiMap() {
        RSetMultimap<String, String> map = redisson.getSetMultimap("mySetMultimap");
        map.put("0", "1");
        map.put("0", "2");
        map.put("3", "4");
        Set<String> allValues = map.get("0");
        System.out.println(allValues);  // [2, 1]
        List<String> newValues = Arrays.asList("7", "6", "5");
        Set<String> oldValues = map.replaceValues("0", newValues);
        System.out.println(oldValues);  // [1, 2]
        Set<String> removedValues = map.removeAll("0");
        System.out.println(removedValues);  // [5, 6, 7]
    }

    /**
     * 基于List的Multimap在保持插入顺序的同时允许一个字段下包含重复的元素
     */
    @Test
    public void testListMultiMap() {
        RListMultimap<String, String> map = redisson.getListMultimap("myListMultimap");
        map.put("0", "1");
        map.put("0", "2");
        map.put("0", "1");
        map.put("3", "4");
        List<String> allValues = map.get("0");
        System.out.println(allValues);  // [1, 2, 1]
        List<String> newValues = Arrays.asList("7", "6", "5");
        List<String> oldValues = map.replaceValues("0", newValues);
        System.out.println(oldValues);  // [1, 2, 1]
        List<String> removedValues = map.removeAll("0");
        System.out.println(removedValues);  // [7, 6, 5]
    }

    /**
     * 多值映射（Multimap）淘汰机制（Eviction）
     */
    @Test
    public void testExpire() throws InterruptedException {
        RSetMultimapCache<String, String> multimap = redisson.getSetMultimapCache("myMultimap");
        multimap.put("1", "a");
        multimap.put("1", "b");
        multimap.put("1", "c");
        multimap.put("2", "e");
        multimap.put("2", "f");
        multimap.expireKey("2", 5, TimeUnit.SECONDS);
        Thread.sleep(5000);
        System.out.println(multimap.get("1").readAll()); // [a, b, c]
        System.out.println(multimap.get("2").readAll()); // []
    }
}
