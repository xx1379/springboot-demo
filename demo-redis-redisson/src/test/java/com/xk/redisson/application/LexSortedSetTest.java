package com.xk.redisson.application;

import org.junit.jupiter.api.Test;
import org.redisson.api.RLexSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2022/4/13 23:31
 */
@SpringBootTest
public class LexSortedSetTest {
    @Autowired
    private RedissonClient redisson;

    @Test
    public void test() throws InterruptedException {
        RLexSortedSet set = redisson.getLexSortedSet("lexSortedSet");
        set.add("d");
        set.addAsync("e");
        set.add("f");
        Thread.sleep(1000);
        System.out.println(set.rangeTail("d", true)); // [d, e, f] 获取d之后的元素，包含d
        System.out.println(set.range("d", true, "e", false)); // [d] 获取d和e之间的元素，包含d，不包含e
    }
}
