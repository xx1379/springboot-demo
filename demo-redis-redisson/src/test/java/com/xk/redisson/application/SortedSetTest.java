package com.xk.redisson.application;

import com.xk.redisson.util.MyComparator;
import org.junit.jupiter.api.Test;
import org.redisson.api.RSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2022/4/13 23:05
 */
@SpringBootTest
public class SortedSetTest {
    @Autowired
    private RedissonClient redisson;

    @Test
    public void test() throws InterruptedException {
        RSortedSet<Integer> set = redisson.getSortedSet("mySortedSet");
        set.trySetComparator(new MyComparator());
        set.add(3);
        set.add(1);
        set.add(2);
        System.out.println(set.readAll());
    }
}
