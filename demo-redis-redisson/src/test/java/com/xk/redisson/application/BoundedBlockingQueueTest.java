package com.xk.redisson.application;

import org.junit.jupiter.api.Test;
import org.redisson.api.RBoundedBlockingQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2022/4/14 21:54
 */
@SpringBootTest
public class BoundedBlockingQueueTest {
    @Autowired
    private RedissonClient redisson;

    @Test
    public void test() throws InterruptedException {
        RBoundedBlockingQueue<String> queue = redisson.getBoundedBlockingQueue("boundedBlockingQueue");
        // 如果初始容量（边界）设定成功则返回`真（true）`，
        // 如果初始容量（边界）已近存在则返回`假（false）`。
        System.out.println(queue.trySetCapacity(2));
        System.out.println(queue.trySetCapacity(2));
        queue.offer("1");
        queue.offer("2");
        new Thread(() -> {
            try {
                // 此时容量已满，下面代码将会被阻塞，直到有空闲为止。
                queue.put("3");
                System.out.println("put 3 into queue");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(2000);
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }
}
