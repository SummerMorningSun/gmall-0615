package com.atguigu.gmall.cart.feign;

import com.atguigu.gmall.sms.api.GmallSmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author erdong
 * @create 2019-11-14 18:46
 */
@FeignClient("sms-service")
public interface GmallSmsClient extends GmallSmsApi {
}
