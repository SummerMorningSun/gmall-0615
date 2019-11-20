package com.atguigu.gmall.pms.feign;

import com.atguigu.gmall.sms.api.GmallSmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author erdong
 * @create 2019-11-05 6:24
 */
@FeignClient("sms-service")
public interface GmallSmsClient extends GmallSmsApi {

    /*@PostMapping("sms/skubounds/sale")
    public Resp<Object> saveSale(@RequestBody SaleVO saleVO);*/

}
