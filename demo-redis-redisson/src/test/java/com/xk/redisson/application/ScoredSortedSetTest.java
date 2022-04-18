package com.xk.redisson.application;

import org.junit.jupiter.api.Test;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2022/4/13 23:25
 */
@SpringBootTest
public class ScoredSortedSetTest {
    @Autowired
    private RedissonClient redisson;

    @Test
    public void test() throws InterruptedException {
        RScoredSortedSet<String> set = redisson.getScoredSortedSet("myScoredSortedSet");
        set.add(0.13, "aaa");
        set.add(0.302, "ccc");
        set.addAsync(0.251, "bbb");
        Thread.sleep(1000);
        int index = set.rank("ccc"); // 2 获取元素在集合中的位置,从0开始
        System.out.println(index);
        Double score = set.getScore("bbb"); // 0.251 获取元素的评分
        System.out.println(score);
        System.out.println(set.pollFirst()); // aaa
        System.out.println(set.pollLast());  // ccc
    }
}
