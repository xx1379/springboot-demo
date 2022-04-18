package com.xk.redisson.application;

import com.xk.redisson.util.MyComparator;
import org.junit.jupiter.api.Test;
import org.redisson.api.RPriorityQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2022/4/14 22:40
 */
@SpringBootTest
public class PriorityQueueTest {
    @Autowired
    private RedissonClient redisson;

    @Test
    public void test() {
        RPriorityQueue<Integer> queue = redisson.getPriorityQueue("myPriorityQueue");
        // 指定对象比较器,倒序。 注意：不能使用内部类、匿名类、lambda表达式，会报错
        queue.trySetComparator(new MyComparator());
        queue.add(3);
        queue.add(1);
        queue.add(2);
        System.out.println(queue.readAll());
    }
}
