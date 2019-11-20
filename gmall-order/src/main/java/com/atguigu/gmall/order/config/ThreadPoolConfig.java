package com.atguigu.gmall.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author erdong
 * @create 2019-11-13 20:22
 */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(1000, 5000, 2, TimeUnit.SECONDS, new ArrayBlockingQueue<>(Integer.MAX_VALUE / 10));
    }

}
