package com.atguigu.gmall.sms.vo;

import lombok.Data;

/**
 * @author erdong
 * @create 2019-11-12 22:46
 */
@Data
public class ItemSaleVO {
    private String type; // 满减 打折 积分

    private String desc; // 优惠信息的具体描述
}
