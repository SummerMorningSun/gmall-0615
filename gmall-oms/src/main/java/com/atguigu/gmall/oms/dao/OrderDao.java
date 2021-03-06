package com.atguigu.gmall.oms.dao;

import com.atguigu.gmall.oms.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author lixianfeng
 * @email lxf@atguigu.com
 * @date 2019-10-31 15:06:14
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

    public int closeOrder(String orderToken);

    int success(String orderToken);
}
