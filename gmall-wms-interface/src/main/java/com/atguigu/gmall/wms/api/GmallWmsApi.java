package com.atguigu.gmall.wms.api;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.wms.entity.WareSkuEntity;
import com.atguigu.gmall.wms.vo.SkuLockVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author erdong
 * @create 2019-11-08 7:52
 */
public interface GmallWmsApi {

    @ApiOperation("获取某个sku的库存信息")
    @GetMapping("wms/waresku/{skuId}")
    public Resp<List<WareSkuEntity>> queryWareskuByskuId(@PathVariable("skuId") Long skuId);

    @PostMapping("wms/waresku/check/lock")
    public Resp<Object> checkAndLock(@RequestBody List<SkuLockVO> skuLockVOS);
}
