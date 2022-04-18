package com.xk.redisson.util;

import java.util.Comparator;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2022/4/14 22:44
 */
public class MyComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2) {
        return o2 - o1;
    }
}
