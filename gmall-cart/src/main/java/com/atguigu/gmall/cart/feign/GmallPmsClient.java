package com.atguigu.gmall.cart.feign;

import com.atguigu.gmall.pms.api.GmallPmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author erdong
 * @create 2019-11-14 18:46
 */
@FeignClient("pms-service")
public interface GmallPmsClient extends GmallPmsApi {
}
