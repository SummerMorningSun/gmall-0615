package com.atguigu.gmall.order.feign;

import com.atguigu.gmall.sms.api.GmallSmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author erdong
 * @create 2019-11-15 19:14
 */
@FeignClient("sms-service")
public interface GmallSmsClient extends GmallSmsApi {
}
