package com.atguigu.gmall.item.feign;

import com.atguigu.gmall.wms.api.GmallWmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author erdong
 * @create 2019-11-13 11:44
 */
@FeignClient("wms-service")
public interface GmallWmsClient extends GmallWmsApi {
}
