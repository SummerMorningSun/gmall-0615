package com.atguigu.gmall.order.feign;

import com.atguigu.gmall.oms.api.GmallOmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author erdong
 * @create 2019-11-17 18:04
 */
@FeignClient("oms-service")
public interface GmallOmsClient extends GmallOmsApi {
}
