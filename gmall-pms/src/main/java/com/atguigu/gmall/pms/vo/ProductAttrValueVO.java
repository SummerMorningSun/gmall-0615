package com.atguigu.gmall.pms.vo;

import com.atguigu.gmall.pms.entity.ProductAttrValueEntity;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author erdong
 * @create 2019-11-03 23:38
 */
@Data
public class ProductAttrValueVO extends ProductAttrValueEntity {

    public void setValueSelected(List<String> valueSelected) {
        // 如果接受的集合为空，则设置不
        if (CollectionUtils.isEmpty(valueSelected)) {
            return;
        }
        this.setAttrValue(StringUtils.join(valueSelected, ","));
    }
}
