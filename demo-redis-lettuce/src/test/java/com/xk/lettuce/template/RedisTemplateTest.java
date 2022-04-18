package com.xk.lettuce.template;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2022/3/24 21:21
 */
@SpringBootTest
public class RedisTemplateTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testKey() {
        // 设置key值，无返回值
        redisTemplate.opsForValue().set("testKey1", "111");
        redisTemplate.opsForValue().set("testKey2", "222");

        // 设置key过期时间,返回是否成功
        Boolean expireRes = redisTemplate.expire("testKey1", 2, TimeUnit.MINUTES);
        System.out.println(expireRes);

        // 获取key过期时间
        Long expire = redisTemplate.getExpire("testKey1");
        System.out.println(expire);

        // 判断key是否存在
        Boolean exists = redisTemplate.hasKey("testKey1");
        System.out.println(exists);

        // 删除单个key, 有则返回true
        Boolean delete1 = redisTemplate.delete("testKey3");
        System.out.println(delete1);

        // 删除多个key，返回删除的个数
        Long delete2 = redisTemplate.delete(Arrays.asList("testKey1", "testKey2"));
        System.out.println(delete2);
    }

    @Test
    public void testString() {
        // 通过ValueOperations设置值
        redisTemplate.opsForValue().set("testString1", 11);

        // 通过BoundValueOperations设置值，可进行一系列的操作而无须“显式”的再次指定Key
        BoundValueOperations boundValueOps = redisTemplate.boundValueOps("testString2");
        boundValueOps.set(22);
        boundValueOps.expire(2, TimeUnit.MINUTES);

        // 通过ValueOperations获取值
        Integer value1 = (Integer) redisTemplate.opsForValue().get("testString1");
        System.out.println(value1);

        // 通过BoundValueOperations获取值
        Integer value2 = (Integer) boundValueOps.get();
        System.out.println(value2);

        // 顺序递增,返回增加后的值
        Long increment = redisTemplate.opsForValue().increment("testString1", 4);
        System.out.println(increment);

        // 顺序递减，返回减少后的值
        Long decrement = boundValueOps.decrement(2);
        System.out.println(decrement);
    }

    @Test
    public void testMap() {
        // hash增加元素
        redisTemplate.opsForHash().put("hashKey", "key1", "value1");
        BoundHashOperations hashKey = redisTemplate.boundHashOps("hashKey");
        hashKey.put("key2", "value2");
        // 添加一个map集合
        HashMap<String, String> hashMap = new HashMap<>() {{
            put("key3", "value3");
            put("key4", "value4");
        }};
        redisTemplate.boundHashOps("hashKey").putAll(hashMap);

        // 获取hash所有key值
        Set hashKey1 = redisTemplate.boundHashOps("hashKey").keys();
        System.out.println(hashKey1);
        Set hashKey2 = redisTemplate.opsForHash().keys("hashKey");
        System.out.println(hashKey2);

        // 获取hash所有value值
        List hashValue1 = redisTemplate.boundHashOps("hashKey").values();
        System.out.println(hashValue1);
        List hashValue2 = redisTemplate.opsForHash().values("hashKey");
        System.out.println(hashValue2);

        // 根据key提取value
        String value1 = (String) redisTemplate.boundHashOps("hashKey").get("key1");
        System.out.println(value1);
        String value2 = (String) redisTemplate.opsForHash().get("hashKey", "key2");
        System.out.println(value2);

        // 删除元素,返回删除元素的个数
        Long delete = redisTemplate.boundHashOps("hashKey").delete("key1");
        System.out.println(delete);

        // 获取所有键值对合集
        Map entries1 = redisTemplate.boundHashOps("hashKey").entries();
        System.out.println(entries1);
        Map entries2 = redisTemplate.opsForHash().entries("hashKey");
        System.out.println(entries2);

        // 判断hash中是否存在某值
        Boolean aBoolean1 = redisTemplate.boundHashOps("hashKey").hasKey("key1");
        System.out.println(aBoolean1);
        Boolean aBoolean2 = redisTemplate.opsForHash().hasKey("hashKey", "key2");
        System.out.println(aBoolean2);
    }

    @Test
    public void testSet() {
        // set增加元素, 返回增加元素的数量
        Long add1 = redisTemplate.opsForSet().add("setKey", "setValue1", "setValue2");
        System.out.println(add1);
        BoundSetOperations boundSetOps = redisTemplate.boundSetOps("setKey");
        Long add2 = boundSetOps.add("setValue3", "setValue4");
        System.out.println(add2);

        // 获取set中元素
        Set members1 = redisTemplate.opsForSet().members("setKey");
        System.out.println(members1);
        Set members2 = boundSetOps.members();
        System.out.println(members2);

        // 判断元素是否存在
        Boolean isMember = redisTemplate.boundSetOps("setKey").isMember("setValue1");
        System.out.println(isMember);

        // 获取集合size
        Long size = redisTemplate.boundSetOps("setKey").size();
        System.out.println(size);

        // 移除指定的元素，返回移除元素的个数
        Long remove = redisTemplate.boundSetOps("setKey").remove("setValue1");
        System.out.println(remove);
    }

    @Test
    public void testList() {
        // list增加单个元素，返回列表长度
        Long push1 = redisTemplate.opsForList().leftPush("listKey", "listValue1");
        System.out.println(push1);
        BoundListOperations boundListOps = redisTemplate.boundListOps("listKey");
        Long push2 = boundListOps.rightPush("listValue2");
        System.out.println(push2);

        // list增加list，返回列表长度
        Long aLong = redisTemplate.opsForList().leftPushAll("listKey", Arrays.asList("listValue3", "listValue4"));
        System.out.println(aLong);

        // 获取list元素
        List range = boundListOps.range(0, 10);
        System.out.println(range);

        // 从左或从右弹出一个元素，返回弹出的元素
        String pop1 = (String) boundListOps.leftPop();
        System.out.println(pop1);
        String pop2 = (String) boundListOps.rightPop();
        System.out.println(pop2);

        // 根据索引查询元素
        String index = (String) boundListOps.index(1);
        System.out.println(index);

        // 获取list长度
        Long size = boundListOps.size();
        System.out.println(size);

        // 根据索引修改List中的某条数据(key，索引，值)
        boundListOps.set(1, "listValue100");

        // 移除元素，移除N个值为value(key,移除个数，值)
        Long value = boundListOps.remove(3, "value");
        System.out.println(value);
    }

    @Test
    public void testZSet() {
        // 向集合中插入元素，并设置分数,返回是否成功
        Boolean add1 = redisTemplate.opsForZSet().add("zSetKey", "zSetValue1", 100d);
        System.out.println(add1);
        BoundZSetOperations boundZSetOps = redisTemplate.boundZSetOps("zSetKey");
        Boolean add2 = boundZSetOps.add("zSetValue2", 99d);
        System.out.println(add2);

        // 向集合中插入多个元素,并设置分数，返回增加的个数
        DefaultTypedTuple<String> p1 = new DefaultTypedTuple<>("zSetValue3", 2.1D);
        DefaultTypedTuple<String> p2 = new DefaultTypedTuple<>("zSetValue4", 3.3D);
        Long add3 = boundZSetOps.add(new HashSet<>(Arrays.asList(p1, p2)));
        System.out.println(add3);

        // 按照排名先后(从小到大)打印指定区间内的元素, -1为打印全部
        Set range = boundZSetOps.range(0, -1);
        System.out.println(range);

        // 获得指定元素的分数
        Double score1 = boundZSetOps.score("zSetValue3");
        System.out.println(score1);

        // 返回集合内的成员个数
        Long size = boundZSetOps.size();
        System.out.println(size);

        // 返回集合内指定分数范围的成员个数（Double类型）
        Long count = boundZSetOps.count(3d, 99.5d);
        System.out.println(count);

        // 返回集合内元素在指定分数范围内的元素，从小到大
        Set set = boundZSetOps.rangeByScore(2d, 99.6d);
        System.out.println(set);
        Set set1 = boundZSetOps.rangeByScoreWithScores(2d, 99.6d);
        System.out.println(set1);

        // 返回集合内元素在指定排名范围内的元素，从小到大
        Set set2 = boundZSetOps.range(1, 4);
        System.out.println(set2);
        Set set3 = boundZSetOps.rangeWithScores(1, 4);
        System.out.println(set3);

        // 返回指定成员的排名，小在前
        Long rank1 = boundZSetOps.rank("zSetValue3");
        System.out.println(rank1);
        Long rank2 = boundZSetOps.reverseRank("zSetValue3");
        System.out.println(rank2);

        // 删除
        // 删除指定元素，返回删除个数
        Long remove1 = boundZSetOps.remove("zSetValue3");
        System.out.println(remove1);
        // 删除指定索引范围的元素，返回删除个数
        Long remove2 = boundZSetOps.removeRange(0, 1);
        System.out.println(remove2);
        // 删除指定分数范围内的元素
        Long remove3 = boundZSetOps.removeRangeByScore(40d, 99.6d);
        System.out.println(remove3);

        // 为指定元素加分，返回变更后的分数
        Double zSetValue1 = boundZSetOps.incrementScore("zSetValue1", 1);
        System.out.println(zSetValue1);
    }
}
