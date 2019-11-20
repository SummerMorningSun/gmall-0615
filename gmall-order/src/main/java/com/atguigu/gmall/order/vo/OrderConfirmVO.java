package com.atguigu.gmall.order.vo;

import com.atguigu.gmall.oms.vo.OrderItemVO;
import com.atguigu.gmall.ums.entity.MemberReceiveAddressEntity;
import lombok.Data;

import java.util.List;

/**
 * @author erdong
 * @create 2019-11-15 18:17
 */
@Data
public class OrderConfirmVO {

    private List<MemberReceiveAddressEntity> address;   // 地址列表

    private List<OrderItemVO> orderItemVOS;  // 订单清单

    private Integer bounds;     // 积分信息

    private String orderToken;  // 防止重复提交



}
