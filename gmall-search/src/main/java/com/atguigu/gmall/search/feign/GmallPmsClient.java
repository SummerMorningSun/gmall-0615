package com.atguigu.gmall.search.feign;

import com.atguigu.gmall.pms.api.GmallPmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author erdong
 * @create 2019-11-08 8:03
 */
@FeignClient("pms-service")
public interface GmallPmsClient extends GmallPmsApi {
}
