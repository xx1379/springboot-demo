package com.xk.banner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author Ke Xiao <xiaokexkxk@126.com>
 * @Date 2021/10/30 12:55
 */
@SpringBootApplication
public class DemoBannerApplication {
    public static void main(String[] args) {
        /**
         *自定义类实现 Banner 接口,在 Spring Boot 启动时设置 Banner 类为自定义类
         */
//        SpringApplication springApplication = new SpringApplication(DemoBannerApplication.class);
//        springApplication.setBanner(new MyBanner());
//        springApplication.run(args);

        /**
         * 隐藏banner
         */
//        SpringApplication springApplication = new SpringApplication(DemoBannerApplication.class);
//        springApplication.setBannerMode(Banner.Mode.OFF);
//        springApplication.run(args);

        SpringApplication.run(DemoBannerApplication.class, args);
    }
}
