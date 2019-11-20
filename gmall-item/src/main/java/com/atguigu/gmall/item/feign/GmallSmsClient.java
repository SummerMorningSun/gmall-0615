package com.atguigu.gmall.item.feign;

import com.atguigu.gmall.sms.api.GmallSmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author erdong
 * @create 2019-11-13 15:58
 */
@FeignClient("sms-service")
public interface GmallSmsClient extends GmallSmsApi {
}
