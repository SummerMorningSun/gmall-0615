package com.atguigu.gmall.pms.service.impl;

import com.atguigu.gmall.pms.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.pms.dao.CategoryDao;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.gmall.pms.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<CategoryEntity> queryCategories(Integer level, Long parentCid) {

        QueryWrapper<CategoryEntity> queryWrapper = new QueryWrapper<>();
        // 安全判断 level 是否等于0 或 null
        if (level != 0) {
            queryWrapper.eq("cat_level", level);  // cat_level:层级 1级，2级，3级
        }
        // 判断父节点id 是否为null
        if (parentCid != null) {
            queryWrapper.eq("parent_cid", parentCid);
        }
        //this.categoryDao.selectList();
        return this.list(queryWrapper);
    }

    @Override
    public List<CategoryVO> listCategoryWithSub(Long pId) {
        List<CategoryVO> categoryVOS= this.categoryDao.listCategoryWithSub(pId);
        return categoryVOS;
    }

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageVo(page);
    }

}