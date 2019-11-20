package com.atguigu.gmall.index.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

/**
 * @author erdong
 * @create 2019-11-12 20:15
 */
@Configuration
public class GmallJedisConfig {
    @Bean
    public JedisPool jedisPool(){

        return new JedisPool("192.168.19.215", 6379);
    }
}
