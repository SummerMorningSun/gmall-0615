package com.atguigu.gmall.pms.service.impl;

import com.atguigu.gmall.pms.dao.*;
import com.atguigu.gmall.pms.entity.*;
import com.atguigu.gmall.pms.feign.GmallSmsClient;
import com.atguigu.gmall.pms.service.*;
import com.atguigu.gmall.pms.vo.ProductAttrValueVO;
import com.atguigu.gmall.pms.vo.SkuInfoVO;
import com.atguigu.gmall.pms.vo.SpuInfoVO;
import com.atguigu.gmall.sms.vo.SaleVO;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import org.springframework.util.CollectionUtils;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private ProductAttrValueService productAttrValueService;

    @Autowired
    private ProductAttrValueDao productAttrValueDao;

    @Autowired
    private SkuInfoDao skuInfoDao;

    @Autowired
    private SkuImagesService skuImagesService;

    @Autowired
    private SkuSaleAttrValueDao skuSaleAttrValueDao;

    @Autowired
    private AttrDao attrDao;

    @Autowired
    private GmallSmsClient smsClient;

    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params)  ,
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageVo(page);
    }

    /**
     * 查询商品列表
     *
     * @param condition
     * @param catId
     * @return
     */
    @Override
    public PageVo querySpuInfo(QueryCondition condition, Long catId) {

        // 封装分页条件
        IPage<SpuInfoEntity> page = new Query<SpuInfoEntity>().getPage(condition);
        // 封装查询条件
        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();
        // 如果分类id不为0，要根据分类id查，否则查全部
        if (catId != 0) {
            wrapper.eq("catalog_id", catId);
        }
        // 如果用户输入了检索条件，根据检索条件查
        String key = condition.getKey();
        if (StringUtils.isNotBlank(key)) {
            wrapper.and(t -> t.like("spu_name", key).or().like("id", key));
        }

        return new PageVo(this.page(page, wrapper));
    }

    /**
     * 可以自定义事务回滚：
     * rollbackFor：的意思是遇见此报错，          rollbackFor=FileNotFoundException
     * 如： FileNotFoundException 文件找不到，回滚，不执行操作
     * noRollbackFor：的意思是即使遇见了此报错，事务照常执行，不回滚。
     *
     * @param spuInfoVO
     * @GlobalTransactional:全局事务注释
     */
    //@Transactional(rollbackFor = Exception.class)
    @GlobalTransactional
    @Override
    public void saveSpuInfoVO(SpuInfoVO spuInfoVO) {
        /// 1.保存spu相关
        // 1.1. 保存spu基本信息 spu_info
        Long spuId = saveSpuInfo(spuInfoVO);

        // 1.2. 保存spu的描述信息 spu_info_desc
        this.spuInfoDescService.saveSpuInfoDesc(spuInfoVO, spuId);

        // 模拟错误，
        //int i = 1 / 0;

        // 1.3. 保存spu的规格参数信息
        saveBaseAttr(spuInfoVO, spuId);

        /// 2. 保存sku相关信息
        saveSkuInfo(spuInfoVO, spuId);

        sendMsg(spuId, "insert");
    }

    private void sendMsg(Long spuId, String type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", spuId);
        map.put("type", type);
        this.amqpTemplate.convertAndSend("GMALL-ITEM-EXCHANGE", "item." + type, map);
    }

    /**
     * 保存sku相关信息
     *
     * @param spuInfoVO
     * @param spuId
     */
    private void saveSkuInfo(SpuInfoVO spuInfoVO, Long spuId) {
        List<SkuInfoVO> skuInfoVOS = spuInfoVO.getSkus();
        if (CollectionUtils.isEmpty(skuInfoVOS)) {
            return;
        }
        skuInfoVOS.forEach(skuInfoVO -> {
            // 2.1. 保存sku基本信息
            SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
            BeanUtils.copyProperties(skuInfoVO, skuInfoEntity);
            // 品牌和分类的id需要从spuInfo中获取
            skuInfoEntity.setBrandId(spuInfoVO.getBrandId());
            skuInfoEntity.setCatalogId(spuInfoVO.getCatalogId());
            // 获取随机的uuid作为sku的编码
            skuInfoEntity.setSkuCode(UUID.randomUUID().toString().substring(0, 10).toLowerCase());
            // 获取图片列表
            List<String> images = skuInfoVO.getImages();
            // 如果图片列表不为null,则设置默认图片
            if (!CollectionUtils.isEmpty(images)) {
                // 设置第一张图片未默认图片
                skuInfoEntity.setSkuDefaultImg(skuInfoEntity.getSkuDefaultImg() == null ?
                        images.get(0) : skuInfoEntity.getSkuDefaultImg());
            }
            skuInfoEntity.setSpuId(spuId);
            this.skuInfoDao.insert(skuInfoEntity);


            // 2.2. 保存sku图片信息
            // 获取skuId
            Long skuId = skuInfoEntity.getSkuId();
            if (!CollectionUtils.isEmpty(images)) {
                String defaultImage = images.get(0);
                List<SkuImagesEntity> skuImagesEntities = images.stream().map(image -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setDefaultImg(StringUtils.equals(defaultImage, image) ? 1 : 0);
                    skuImagesEntity.setSkuId(skuId);
                    skuImagesEntity.setImgSort(0);
                    skuImagesEntity.setImgUrl(image);
                    return skuImagesEntity;
                }).collect(Collectors.toList());
                this.skuImagesService.saveBatch(skuImagesEntities);
            }
            // 2.3. 保存sku的规格参数（销售属性）
            List<SkuSaleAttrValueEntity> saleAttrs = skuInfoVO.getSaleAttrs();
            saleAttrs.forEach(saleAttr -> {
                // 设置属性名，需要根据id查询AttrEntity
                saleAttr.setAttrName(this.attrDao.selectById(saleAttr.getAttrId()).getAttrName());
                saleAttr.setAttrSort(0);
                saleAttr.setSkuId(skuId);
                skuSaleAttrValueDao.insert(saleAttr);
            });
            //this.skuSaleAttrValueService.saveBatch(saleAttrs);  不能进行批量处理，总是报错500

            // 3. 保存营销相关信息，需要远程调用gmall-sms
            SaleVO saleVO = new SaleVO();
            BeanUtils.copyProperties(skuInfoVO, saleVO);
            saleVO.setSkuId(skuId);
            this.smsClient.saveSale(saleVO);
        });
    }

    private void saveBaseAttr(SpuInfoVO spuInfoVO, Long spuId) {
        List<ProductAttrValueVO> baseAttrs = spuInfoVO.getBaseAttrs();
        baseAttrs.forEach(baseAttr -> {
            baseAttr.setSpuId(spuId);
            baseAttr.setAttrSort(0);
            baseAttr.setQuickShow(1);
            this.productAttrValueDao.insert(baseAttr);
        });
    }


    private Long saveSpuInfo(SpuInfoVO spuInfoVO) {
        //spuInfoVO.setPublishStatus(1); // 默认是已上架
        spuInfoVO.setCreateTime(new Date());
        spuInfoVO.setUodateTime(spuInfoVO.getCreateTime()); // 新增时，更新时间和创建时间一致
        this.save(spuInfoVO);
        return spuInfoVO.getId();
    }

}