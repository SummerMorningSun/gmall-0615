package com.atguigu.gmall.pms.vo;

import com.atguigu.gmall.pms.entity.AttrEntity;
import lombok.Data;

/**
 * @author erdong
 * @create 2019-11-03 15:27
 */
@Data
public class AttrVO extends AttrEntity {

    private Long attrGroupId;  // 扩展分组Id

}
