package com.atguigu.gmall.item.feign;

import com.atguigu.gmall.pms.api.GmallPmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author erdong
 * @create 2019-11-13 11:43
 */
@FeignClient("pms-service")
public interface GmallPmsClient extends GmallPmsApi {
}
