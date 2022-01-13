package com.xk.redis.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2022/1/13 23:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {  // 对象一般都要序列化
    private String name;

    private Integer age;
}
