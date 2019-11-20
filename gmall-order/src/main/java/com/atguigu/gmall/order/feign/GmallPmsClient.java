package com.atguigu.gmall.order.feign;

import com.atguigu.gmall.pms.api.GmallPmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author erdong
 * @create 2019-11-15 19:12
 */
@FeignClient("pms-service")
public interface GmallPmsClient extends GmallPmsApi {
}
