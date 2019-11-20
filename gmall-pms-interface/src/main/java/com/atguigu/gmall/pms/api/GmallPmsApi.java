package com.atguigu.gmall.pms.api;

import com.atguigu.core.bean.QueryCondition;
import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.pms.entity.*;
import com.atguigu.gmall.pms.vo.CategoryVO;
import com.atguigu.gmall.pms.vo.GroupVO;
import com.atguigu.gmall.pms.vo.SpuAttributeValueVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * @author erdong
 * @create 2019-11-08 7:36
 */
public interface GmallPmsApi {

    @GetMapping("pms/skusaleattrvalue/sku/{skuId}")
    public Resp<List<SkuSaleAttrValueEntity>> querySaleAttrBySkuId(@PathVariable("skuId")Long skuId);

    @GetMapping("pms/skuimages/{skuId}")
    public Resp<List<String>> listSkuImagesBySkuId(@PathVariable("skuId") Long skuId);

    @ApiOperation("skuId详情查询")
    @GetMapping("pms/skuinfo/info/{skuId}")
    public Resp<SkuInfoEntity> querySkuById(@PathVariable("skuId") Long skuId);

    @GetMapping("pms/skusaleattrvalue/{spuId}")
    public Resp<List<SkuSaleAttrValueEntity>> querySaleAttrValues(@PathVariable("spuId") Long spuId);

    @ApiOperation("详情查询")
    @GetMapping("pms/spuinfodesc/info/{spuId}")
    public Resp<SpuInfoDescEntity> querySpuDescById(@PathVariable("spuId") Long spuId);

    @GetMapping("pms/attrgroup/item/group/{cId}/{spuId}")
    public Resp<List<GroupVO>> queryGroupVOBycId(@PathVariable("cId") Long cId, @PathVariable("spuId")Long spuId);

    @ApiOperation("id详情查询")
    @GetMapping("pms/spuinfo/info/{id}")
    public Resp<SpuInfoEntity> querySpuById(@PathVariable("id") Long id);

    @ApiOperation("分页查询(排序)")
    @PostMapping("pms/spuinfo/list")
    public Resp<List<SpuInfoEntity>> querySpuPage(@RequestBody QueryCondition queryCondition);

    @ApiOperation("查询spu下的sku")
    @GetMapping("pms/spuinfo/{spuId}")
    public Resp<List<SkuInfoEntity>> querySkuBySpuId(@PathVariable("spuId") Long spuId);

    @ApiOperation("brand详情查询")
    @GetMapping("pms/brand/info/{brandId}")
    public Resp<BrandEntity> queryBrandById(@PathVariable("brandId") Long brandId);

    @ApiOperation("cat详情查询")
    @GetMapping("pms/category/info/{catId}")
    public Resp<CategoryEntity> queryCategoryById(@PathVariable("catId") Long catId);

    @ApiOperation("查询一级分类")
    @GetMapping("pms/category")
    public Resp<List<CategoryEntity>> queryCategories(@RequestParam(value = "level", defaultValue = "0") Integer level, @RequestParam(value = "parentCid", required = false) Long parentCid);

    @ApiOperation("查询二三级分类")
    @GetMapping("pms/category/{pId}")
    public Resp<List<CategoryVO>> listCategoryWithSub(@PathVariable("pId") Long pId);

    @GetMapping("pms/productattrvalue/{spuId}")
    public Resp<List<SpuAttributeValueVO>> querySearchAttrValue(@PathVariable("spuId") Long spuId);

}
