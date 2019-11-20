package com.atguigu.gmall.index.feign;

import com.atguigu.gmall.pms.api.GmallPmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author erdong
 * @create 2019-11-11 20:13
 */
@FeignClient("pms-service")
public interface GmallPmsClient extends GmallPmsApi {
}
