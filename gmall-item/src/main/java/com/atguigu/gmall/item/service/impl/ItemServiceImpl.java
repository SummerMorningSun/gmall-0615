package com.atguigu.gmall.item.service.impl;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.item.feign.GmallPmsClient;
import com.atguigu.gmall.item.feign.GmallSmsClient;
import com.atguigu.gmall.item.feign.GmallWmsClient;
import com.atguigu.gmall.item.service.ItemService;
import com.atguigu.gmall.item.vo.ItemVO;
import com.atguigu.gmall.pms.entity.*;
import com.atguigu.gmall.pms.vo.GroupVO;
import com.atguigu.gmall.sms.vo.ItemSaleVO;
import com.atguigu.gmall.wms.entity.WareSkuEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author erdong
 * @create 2019-11-12 22:49
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private GmallWmsClient gmallWmsClient;

    @Autowired
    private GmallPmsClient gmallPmsClient;

    @Autowired
    private GmallSmsClient gmallSmsClient;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public ItemVO getItem(Long skuId) {
        ItemVO itemVO = new ItemVO();

        // 1.查询sku信息
        AtomicReference<Long> spuId = null;
        CompletableFuture<SkuInfoEntity> skuCompletableFuture = CompletableFuture.supplyAsync(() -> {
            Resp<SkuInfoEntity> skuInfoEntityResp = this.gmallPmsClient.querySkuById(skuId);
            SkuInfoEntity skuInfoEntity = skuInfoEntityResp.getData();
            BeanUtils.copyProperties(skuInfoEntity, itemVO);
            return skuInfoEntity;
        }, threadPoolExecutor);

        CompletableFuture<Void> brandCompletableFuture = skuCompletableFuture.thenAcceptAsync(skuInfoEntity -> {
            // 2.品牌
            Resp<BrandEntity> brandEntityResp = this.gmallPmsClient.queryBrandById(skuInfoEntity.getBrandId());
            itemVO.setBrand(brandEntityResp.getData());
        }, threadPoolExecutor);

        CompletableFuture<Void> categoryCompletableFuture = skuCompletableFuture.thenAcceptAsync(skuInfoEntity -> {
            // 3.分类
            Resp<CategoryEntity> categoryEntityResp = this.gmallPmsClient.queryCategoryById(skuInfoEntity.getCatalogId());
            itemVO.setCategory(categoryEntityResp.getData());
        }, threadPoolExecutor);

        CompletableFuture<Void> spuInfoCompletableFuture = skuCompletableFuture.thenAcceptAsync(skuInfoEntity -> {
            // 4.spu信息
            Resp<SpuInfoEntity> spuInfoEntityResp = this.gmallPmsClient.querySpuById(skuInfoEntity.getSpuId());
            itemVO.setSpuInfo(spuInfoEntityResp.getData());
        }, threadPoolExecutor);

        // 5.设置图片信息
        CompletableFuture<Void> skuImagesCompletableFuture = CompletableFuture.runAsync(() -> {
            Resp<List<String>> skuImagesInfo = this.gmallPmsClient.listSkuImagesBySkuId(skuId);
            itemVO.setPics(skuImagesInfo.getData());
        }, threadPoolExecutor);

        // 6.营销信息
        CompletableFuture<Void> itemSaleCompletableFuture = CompletableFuture.runAsync(() -> {
            Resp<List<ItemSaleVO>> itemSaleVOs = this.gmallSmsClient.queryItemSaleVOs(skuId);
            itemVO.setSales(itemSaleVOs.getData());
        }, threadPoolExecutor);

        // 7.是否有货
        CompletableFuture<Void> wareSkuCompletableFuture = CompletableFuture.runAsync(() -> {
            Resp<List<WareSkuEntity>> wareskuByskuId = this.gmallWmsClient.queryWareskuByskuId(skuId);
            List<WareSkuEntity> wareskuByskuIdData = wareskuByskuId.getData();
            boolean b = wareskuByskuIdData.stream().anyMatch(t -> t.getStock() > 0);
            itemVO.setStore(b);
        }, threadPoolExecutor);

        // 8.spu所有的销售属性
        CompletableFuture<Void> skuSaleCompletableFuture = skuCompletableFuture.thenAcceptAsync(skuInfoEntity -> {
            Resp<List<SkuSaleAttrValueEntity>> saleAttrValues = this.gmallPmsClient.querySaleAttrValues(skuInfoEntity.getSpuId());
            itemVO.setSkuSales(saleAttrValues.getData());
        }, threadPoolExecutor);

        // 9.spu的描述信息
        CompletableFuture<Void> spuInfoDecCompletableFuture = skuCompletableFuture.thenAcceptAsync(skuInfoEntity -> {
            Resp<SpuInfoDescEntity> spuInfoDescEntityResp = this.gmallPmsClient.querySpuDescById(skuInfoEntity.getSpuId());
            itemVO.setDesc(spuInfoDescEntityResp.getData());
        }, threadPoolExecutor);

        // 10.规格属性分组及组下的规格参数及值
        CompletableFuture<Void> groupCompletableFuture = skuCompletableFuture.thenAcceptAsync(skuInfoEntity -> {
            Resp<List<GroupVO>> listResp = this.gmallPmsClient.queryGroupVOBycId(skuInfoEntity.getCatalogId(), skuInfoEntity.getSpuId());
            itemVO.setGroups(listResp.getData());
        }, threadPoolExecutor);

        CompletableFuture.allOf(brandCompletableFuture, categoryCompletableFuture, spuInfoCompletableFuture, skuImagesCompletableFuture,
                itemSaleCompletableFuture, wareSkuCompletableFuture, skuSaleCompletableFuture, spuInfoDecCompletableFuture, groupCompletableFuture).join();

        return itemVO;
    }
}





