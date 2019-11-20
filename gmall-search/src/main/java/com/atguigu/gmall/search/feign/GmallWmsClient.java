package com.atguigu.gmall.search.feign;

import com.atguigu.gmall.wms.api.GmallWmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author erdong
 * @create 2019-11-08 8:04
 */
@FeignClient("wms-service")
public interface GmallWmsClient extends GmallWmsApi {
}
