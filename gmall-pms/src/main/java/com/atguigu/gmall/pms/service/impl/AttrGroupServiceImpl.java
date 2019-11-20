package com.atguigu.gmall.pms.service.impl;

import com.atguigu.gmall.pms.dao.AttrAttrgroupRelationDao;
import com.atguigu.gmall.pms.dao.AttrDao;
import com.atguigu.gmall.pms.dao.ProductAttrValueDao;
import com.atguigu.gmall.pms.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gmall.pms.entity.AttrEntity;
import com.atguigu.gmall.pms.entity.ProductAttrValueEntity;
import com.atguigu.gmall.pms.vo.AttrGroupVO;
import com.atguigu.gmall.pms.vo.GroupVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.pms.dao.AttrGroupDao;
import com.atguigu.gmall.pms.entity.AttrGroupEntity;
import com.atguigu.gmall.pms.service.AttrGroupService;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrAttrgroupRelationDao relationDao;

    @Autowired
    private AttrDao attrDao;

    @Autowired
    private ProductAttrValueDao productAttrValueDao;

    /**
     * 查询组及组的规格参数
     * @param gid
     * @return
     */
    @Override
    public AttrGroupVO queryAttrGroupAndWithatterBygid(Long gid) {
        AttrGroupVO groupVO = new AttrGroupVO();

        // 先查询分组 根据接收的gid查询分组  属性和组的关系id：attr_group_id
        AttrGroupEntity groupEntity = this.getById(gid);       //拿到分组对象
        BeanUtils.copyProperties(groupEntity, groupVO);        //拷贝

        // 再根据组和属性的关系id: attr_group_id  查询属性id: att_id
        List<AttrAttrgroupRelationEntity> relationEntities = this.relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", gid));
        if (CollectionUtils.isEmpty(relationEntities)) {
            return groupVO;    // 如果为空，直接返回分组对象即可，以为 没有组和属性的关系id的话，也就没有什么属性
        }
        // 把组合和属性的关联关系塞到groupVO里面
        groupVO.setRelations(relationEntities);

        // 再根据属性id：att_id 查询属性的详细信息    --遍历每一个元素过滤掉其他的只留下 attr_id
        List<Long> attrIds = relationEntities.stream().map(relation -> relation.getAttrId()).collect(Collectors.toList());
        List<AttrEntity> attrEntities = this.attrDao.selectBatchIds(attrIds);
        groupVO.setAttrEntities(attrEntities);
        return groupVO;
    }

    @Override
    public List<AttrGroupVO> queryByCid(Long cid) {
        // 查询所有的分组
        List<AttrGroupEntity> attrGroupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", cid));

        // 查询出每组下的规格参数
        List<AttrGroupVO> attrGroupVOs = attrGroupEntities.stream().map(attrGroupEntity -> {
            return this.queryAttrGroupAndWithatterBygid(attrGroupEntity.getAttrGroupId());
        }).collect(Collectors.toList());

        return attrGroupVOs;
    }

    @Override
    public List<GroupVO> queryGroupVOBycId(Long cId, Long spuId) {
        List<AttrGroupEntity> attrGroupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", cId));
        if (CollectionUtils.isEmpty(attrGroupEntities)){
            return null;
        }
        return attrGroupEntities.stream().map(attrGroupEntity -> {
            GroupVO groupVO = new GroupVO();
            groupVO.setGroupName(attrGroupEntity.getAttrGroupName());
            List<ProductAttrValueEntity> productAttrValueEntities = this.productAttrValueDao.queryByGidAndSpuId(spuId, attrGroupEntity.getAttrGroupId());
            groupVO.setBaseAttrValues(productAttrValueEntities);
            return groupVO;
        }).collect(Collectors.toList());
    }

    /**
     * 三级分类查询属性分组
     *
     * @param catId
     * @param condition
     * @return
     */
    @Override
    public PageVo queryByCidPage(Long catId, QueryCondition condition) {
        QueryWrapper<AttrGroupEntity> queryWrapper = new QueryWrapper<>();

        if (catId != null) {
            queryWrapper.eq("catelog_id", catId);  //catelogId 所属分类id
        }

        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(condition),
                queryWrapper
        );

        return new PageVo(page);
    }

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageVo(page);
    }

}