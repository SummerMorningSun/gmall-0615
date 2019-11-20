package com.atguigu.gmall.order.service;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.oms.entity.OrderEntity;
import com.atguigu.gmall.oms.vo.OrderSubmitVO;
import com.atguigu.gmall.order.vo.OrderConfirmVO;

/**
 * @author erdong
 * @create 2019-11-15 19:06
 */
public interface OrderService {
    OrderConfirmVO confirm();

    OrderEntity submit(OrderSubmitVO orderSubmitVO);

    void paySuccess(String out_trade_no);

    OrderEntity queryOrder();
}
