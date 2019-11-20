package com.atguigu.gmall.pms.dao;

import com.atguigu.gmall.pms.entity.ProductAttrValueEntity;
import com.atguigu.gmall.pms.vo.ProductAttrValueVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * spu属性值
 * 
 * @author lixianfeng
 * @email lxf@atguigu.com
 * @date 2019-10-31 14:56:52
 */
@Mapper
public interface ProductAttrValueDao extends BaseMapper<ProductAttrValueEntity> {
    List<ProductAttrValueEntity> querySearchAttrValue(Long spuId);

    List<ProductAttrValueEntity> queryByGidAndSpuId(@Param("spuId") Long spuId,@Param("groupId") Long attrGroupId);
}
