package com.atguigu.gmall.item.service;

import com.atguigu.gmall.item.vo.ItemVO;

/**
 * @author erdong
 * @create 2019-11-12 22:49
 */
public interface ItemService {
    ItemVO getItem(Long skuId);
}
