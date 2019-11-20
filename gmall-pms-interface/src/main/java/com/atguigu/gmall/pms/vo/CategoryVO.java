package com.atguigu.gmall.pms.vo;

import com.atguigu.gmall.pms.entity.CategoryEntity;
import lombok.Data;

import java.util.List;

/**
 * @author erdong
 * @create 2019-11-11 21:11
 */
@Data
public class CategoryVO extends CategoryEntity {

    private List<CategoryEntity> subs;
}
