package com.atguigu.gmall.oms.feign;

import com.atguigu.gmall.pms.api.GmallPmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author erdong
 * @create 2019-11-17 17:41
 */
@FeignClient("pms-service")
public interface GmallPmsClient extends GmallPmsApi {
}
