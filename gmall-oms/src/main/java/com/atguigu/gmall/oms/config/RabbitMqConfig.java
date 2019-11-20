package com.atguigu.gmall.oms.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author erdong
 * @create 2019-11-17 16:09
 */
@Configuration
public class RabbitMqConfig {
    @Bean
    public Exchange exchange() {
        // 引入交换机
        return new TopicExchange("OMS-EXCHANGE", true, false, null);
    }

    @Bean
    public Queue queue() {
        // 引入消息队列
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "OMS-EXCHANGE");
        arguments.put("x-dead-letter-routing-key", "oms.dead"); // 死信队列
        arguments.put("x-message-ttl", 100000); // 毫秒   生成订单（比较耗时间）
        return new Queue("OMS-TTL-QUEUE", true, false, false, arguments);
    }

    @Bean
    public Binding binding() {
        // 引入消息队列绑定交换机
        return new Binding("OMS-TTL-QUEUE", Binding.DestinationType.QUEUE, "OMS-EXCHANGE", "oms.close", null);
    }

    @Bean
    public Queue deadQueue() {
        // 引入（死信）消息队列
        return new Queue("OMS-DEAD-QUEUE", true, false, false, null);
    }

    @Bean
    public Binding deadBinding() {
        // 引入绑定死信绑定交换机
        return new Binding("OMS-DEAD-QUEUE", Binding.DestinationType.QUEUE, "OMS-EXCHANGE", "oms.dead", null);
    }
}
