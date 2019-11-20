package com.atguigu.gmall.pms;


import com.atguigu.gmall.pms.dao.BrandDao;
import com.atguigu.gmall.pms.entity.BrandEntity;
import com.atguigu.gmall.pms.service.BrandService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
class GmallPmsApplicationTests {

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private BrandService brandService;

    @Test
    void contextLoads() {
    }

    @Test
    public void test01() {
//        BrandEntity brandEntity = new BrandEntity();
//        brandEntity.setDescript("二东");
//        brandEntity.setFirstLetter("东");
//        brandEntity.setLogo("www.baidu.com/log.gif");
//        brandEntity.setName("小东");
//        brandEntity.setShowStatus(0);
//        brandEntity.setSort(1);
//        this.brandDao.insert(brandEntity);
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("name","fds发多少");
//        this.brandDao.deleteByMap(map);
        // new QueryWrapper<BrandEntity>().like.lt,lg......  模糊查询，大小
//        List<BrandEntity> list = this.brandDao.selectList(new QueryWrapper<BrandEntity>().eq("name", "东东"));
//        System.out.println(list);
        IPage<BrandEntity> page = this.brandService.page(new Page<BrandEntity>(2L, 2L), new QueryWrapper<>());
        System.out.println(page.getRecords());
        System.out.println(page.getTotal());
        System.out.println(page.getPages());
    }

}
