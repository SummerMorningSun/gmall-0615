package com.atguigu.gmall.oms.listener;

import com.atguigu.gmall.oms.entity.OrderEntity;
import com.atguigu.gmall.oms.service.OrderService;
import com.atguigu.gmall.ums.vo.UserBoundVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 观单监听器
 *
 * @author erdong
 * @create 2019-11-18 23:19
 */
@Component
public class Omslistener {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private OrderService orderService;

    @RabbitListener(queues = "OMS-DEAD-QUEUE")
    public void closeOrder(String orderToken) {

        // 关闭订单的状态为已关闭
        if (this.orderService.closeOrder(orderToken) == 1) {
            // 发送消息给库存，解锁库存
            this.amqpTemplate.convertAndSend("wms-exchange", "wms.ttl", orderToken);
        }
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "ORDER-SUCCESS-QUEUE", durable = "true"),
            exchange = @Exchange(value = "GMALL-ORDER-EXCHANGE", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"order.pay"}
    ))
    public void paySuccess(String orderToken) {  // 支付成功减库存

        // 更新订单的状态
        if (this.orderService.success(orderToken) == 1) {
            // 减库存
            this.amqpTemplate.convertAndSend("WMS-EXCHANGE", "stock.minus", orderToken);

            // 给用户加积分 TODO 自己写
            UserBoundVO userBoundVO = new UserBoundVO();
            OrderEntity orderEntity = this.orderService.getOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderToken));
            userBoundVO.setUserId(orderEntity.getMemberId());
            userBoundVO.setGrouth(orderEntity.getGrowth());
            userBoundVO.setIntegration(orderEntity.getIntegration());
            this.amqpTemplate.convertAndSend("ums-exchange", "user.bound", userBoundVO);
        }
    }

}
