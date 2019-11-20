package com.atguigu.gmall.order.feign;

import com.atguigu.gmall.ums.api.GmallUmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author erdong
 * @create 2019-11-15 19:16
 */
@FeignClient("ums-service")
public interface GmallUmsClient extends GmallUmsApi {
}
