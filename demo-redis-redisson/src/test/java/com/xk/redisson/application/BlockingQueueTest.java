package com.xk.redisson.application;

import org.junit.jupiter.api.Test;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2022/4/14 21:43
 */
@SpringBootTest
public class BlockingQueueTest {
    @Autowired
    private RedissonClient redisson;

    /**
     * 阻塞队列内部实现就是使用list, blpop
     */
    @Test
    public void test() throws InterruptedException {
        RBlockingQueue<String> queue = redisson.getBlockingQueue("blockingQueue");
        queue.offer("aaa");
        String obj = queue.peek();
        System.out.println(obj);
        obj = queue.poll();
        System.out.println(obj);
        new Thread(() -> {
            while (true) {
                try {
                    String take = queue.take();
                    System.out.println(Thread.currentThread().getName() + "----" + take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(1000);
        queue.offer("bbb");
        Thread.sleep(1000);
        queue.offer("ccc");
    }
}
