package com.atguigu.gmall.index.service;

import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.gmall.pms.vo.CategoryVO;

import java.util.List;

/**
 * @author erdong
 * @create 2019-11-11 20:36
 */
public interface IndexService {

    List<CategoryEntity> listCategory_01();

    List<CategoryVO> listCategory_02_03(Long pId);
}
