package com.atguigu.gmall.wms.config;

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
        return new TopicExchange("WMS-EXCHANGE", true, false, null);
    }

    @Bean
    public Queue queue() {
        // 引入消息队列
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "WMS-EXCHANGE");
        arguments.put("x-dead-letter-routing-key", "wms.ttl");
        arguments.put("x-message-ttl", 120000); // 毫秒       // 锁库的时间限制 超过这个时间没有支付，就不再锁库，释放资源
        return new Queue("WMS-TTL-QUEUE", true, false, false, arguments);
    }

    @Bean
    public Binding binding() {
        // 引入消息队列绑定交换机
        return new Binding("WMS-TTL-QUEUE", Binding.DestinationType.QUEUE, "WMS-EXCHANGE", "wms.unlock", null);
    }

    @Bean
    public Queue deadQueue() {
        // 引入（死信）消息队列
        return new Queue("WMS-DEAD-QUEUE", true, false, false, null);
}

    @Bean
    public Binding deadBinding() {
        // 引入绑定死信绑定交换机
        return new Binding("WMS-DEAD-QUEUE", Binding.DestinationType.QUEUE, "WMS-EXCHANGE", "wms.ttl", null);
    }
}
