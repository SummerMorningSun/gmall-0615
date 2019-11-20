package com.atguigu.gmall.pms.service.impl;

import com.atguigu.gmall.pms.dao.AttrAttrgroupRelationDao;
import com.atguigu.gmall.pms.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gmall.pms.service.AttrAttrgroupRelationService;
import com.atguigu.gmall.pms.vo.AttrVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.pms.dao.AttrDao;
import com.atguigu.gmall.pms.entity.AttrEntity;
import com.atguigu.gmall.pms.service.AttrService;
import org.springframework.transaction.annotation.Transactional;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationService relationService;

    /**
     * @param attrVO
     * @return
     */
    /*@Transactional
    @Override
    public boolean updateAttrAndRelation(AttrVO attrVO) {
        Wrapper<AttrVO> wrapper = new UpdateWrapper<>();
        attrVO.setAttrGroupId(attrVO.getAttrGroupId());
        attrVO.setAttrId(attrVO.getAttrId());
        attrVO.setAttrName(attrVO.getAttrName());
        attrVO.setAttrType(attrVO.);
        boolean b = this.updateById(attrVO);
        // 首先修改属性表 pms_attr
        boolean w = this.update(attrVO, attrVO.getAttrId());
        // 再修改关联表
        AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
        relationEntity.setAttrGroupId(attrVO.getAttrGroupId());   // 重点字段
        relationEntity.setAttrId(attrVO.getAttrId());
        relationEntity.setAttrSort(0);
        boolean u = this.relationService.updateById(relationEntity);

        if (!w || !u) {
            return false;
        }
        return true;
    }*/

    /**
     * 保存规格参数 并且 关联分组
     *
     * @param attrVO
     */
    @Transactional
    @Override
    public boolean saveAttrAndRelation(AttrVO attrVO) {

        // 插入 pms_attr表
        boolean a = this.save(attrVO);

        // 插入 中间表：pms_attr_attrgroup_relation
        AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
        relationEntity.setAttrGroupId(attrVO.getAttrGroupId());   // 重点字段
        relationEntity.setAttrId(attrVO.getAttrId());
        relationEntity.setAttrSort(0);
        boolean r = this.relationService.save(relationEntity);

        if (!a || !r) {
            return false;
        }
        return true;
    }

    /**
     * 根据三级分类查询规格分类参数及查询组及组下面的规格参数
     *
     * @param type
     * @param cid
     * @param queryCondition
     * @return
     */
    @Override
    public PageVo queryAttrByCid(Integer type, Long cid, QueryCondition queryCondition) {
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();

        if (type != null) {
            wrapper.eq("attr_type", type);
        }

        if (cid != null) {
            wrapper.eq("catelog_id", cid);
        }

        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(queryCondition),
                wrapper
        );

        return new PageVo(page);
    }

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageVo(page);
    }

}