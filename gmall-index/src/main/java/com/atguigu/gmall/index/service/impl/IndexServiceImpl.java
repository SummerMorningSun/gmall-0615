package com.atguigu.gmall.index.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.index.annotation.GmallCache;
import com.atguigu.gmall.index.feign.GmallPmsClient;
import com.atguigu.gmall.index.service.IndexService;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.gmall.pms.vo.CategoryVO;
import com.mysql.cj.util.TimeUtil;
import jdk.nashorn.internal.scripts.JS;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author erdong
 * @create 2019-11-11 20:36
 */
@Service
public class IndexServiceImpl implements IndexService {
    private static final String KEY_PREFIX = "index:category:";

    @Autowired
    private GmallPmsClient gmallPmsClient;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public List<CategoryEntity> listCategory_01() {
        Resp<List<CategoryEntity>> listResp = this.gmallPmsClient.queryCategories(1, null);
        return listResp.getData();
    }

    @Override
    @GmallCache(prefix = KEY_PREFIX, timeout = 300000l, random = 50000l)
    public List<CategoryVO> listCategory_02_03(Long pId) {
        // 1. 查询缓存，缓存中有的话直接返回
//        String cache = this.redisTemplate.opsForValue().get(KEY_PREFIX + pid);
//        if (StringUtils.isNotBlank(cache)) {
//            return JSON.parseArray(cache, CategoryVO.class);
//        }

        // 2. 如果缓存中没有，查询数据库
        Resp<List<CategoryVO>> listResp = this.gmallPmsClient.listCategoryWithSub(pId);
        List<CategoryVO> categoryVOS = listResp.getData();

//        // 3. 查询完成之后，放入缓存
//        this.redisTemplate.opsForValue().set(KEY_PREFIX + pid, JSON.toJSONString(categoryVOS), 5 + (int) (Math.random() * 5), TimeUnit.DAYS);

        return categoryVOS;
    }

}
