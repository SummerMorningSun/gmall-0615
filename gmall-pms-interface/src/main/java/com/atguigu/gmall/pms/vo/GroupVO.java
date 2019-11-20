package com.atguigu.gmall.pms.vo;

import com.atguigu.gmall.pms.entity.ProductAttrValueEntity;
import lombok.Data;

import java.util.List;

/**
 * @author erdong
 * @create 2019-11-12 21:52
 */
@Data
public class GroupVO {
    private String groupName;

    private List<ProductAttrValueEntity> baseAttrValues;
}
