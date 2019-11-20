package com.atguigu.gmall.index.controller;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.index.service.IndexService;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.gmall.pms.vo.CategoryVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author erdong
 * @create 2019-11-11 20:16
 */
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private IndexService indexService;

    /**
     * 列表类别一级分类
     *
     * @return
     */
    @GetMapping("cates")
    public Resp<List<CategoryEntity>> listCategory_01() {
        List<CategoryEntity> categoryEntityList = this.indexService.listCategory_01();
        return Resp.ok(categoryEntityList);
    }

    /**
     * 列表类表二三级分类
     *
     * @return
     */
    @ApiOperation("父id查询二级分类及其子分类")
    @GetMapping("cates/{pId}")
    public Resp<List<CategoryVO>> listCategory_02_03(@PathVariable("pId") Long pId) {
        List<CategoryVO> listCategory_02_03 = indexService.listCategory_02_03(pId);
        return Resp.ok(listCategory_02_03);
    }
}
