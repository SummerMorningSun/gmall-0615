package com.atguigu.gmall.pms.service.impl;

import com.atguigu.gmall.pms.vo.ProductAttrValueVO;
import com.atguigu.gmall.pms.vo.SpuAttributeValueVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.pms.dao.ProductAttrValueDao;
import com.atguigu.gmall.pms.entity.ProductAttrValueEntity;
import com.atguigu.gmall.pms.service.ProductAttrValueService;
import sun.rmi.runtime.Log;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {

    @Autowired
    private ProductAttrValueDao attrValueDao;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageVo(page);
    }

    /**
     * 查询所有的，可以分为已上架和未上架的
     * @param spuId
     * @return
     */
    @Override
    public List<SpuAttributeValueVO> querySearchAttrValue(Long spuId) {
        List<ProductAttrValueEntity> productAttrValueEntities = this.attrValueDao.querySearchAttrValue(spuId);
        return productAttrValueEntities.stream().map(productAttrValueEntity -> {
            SpuAttributeValueVO spuAttributeValueVO = new SpuAttributeValueVO();
            spuAttributeValueVO.setProductAttributeId(productAttrValueEntity.getAttrId());
            spuAttributeValueVO.setName(productAttrValueEntity.getAttrName());
            spuAttributeValueVO.setValue(productAttrValueEntity.getAttrValue());
            return spuAttributeValueVO;
        }).collect(Collectors.toList());
    }

}