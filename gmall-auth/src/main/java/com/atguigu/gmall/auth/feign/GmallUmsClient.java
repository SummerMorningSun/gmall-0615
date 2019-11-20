package com.atguigu.gmall.auth.feign;

import com.atguigu.gmall.ums.api.GmallUmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author erdong
 * @create 2019-11-14 15:33
 */
@FeignClient("ums-service")
public interface GmallUmsClient extends GmallUmsApi {
}
