package com.atguigu.gmall.pms.controller;

import java.util.Arrays;
import java.util.Map;


import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;
import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.pms.vo.AttrVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gmall.pms.entity.AttrEntity;
import com.atguigu.gmall.pms.service.AttrService;


/**
 * 商品属性
 *
 * @author lixianfeng
 * @email lxf@atguigu.com
 * @date 2019-10-31 14:56:52
 */
@Api(tags = "商品属性 管理")
@RestController
@RequestMapping("pms/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    /**
     * 根据三级分类查询规格分类参数及查询组及组下面的规格参数
     *
     * @param type
     * @param cid
     * @param queryCondition
     * @return
     */
    @GetMapping
    public Resp<PageVo> queryAttrByCid(@RequestParam(value = "type", required = false) Integer type, @RequestParam(value = "cid", required = false) Long cid, QueryCondition queryCondition) {
        PageVo pageVo = this.attrService.queryAttrByCid(type, cid, queryCondition);
        return Resp.ok(pageVo);
    }

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('pms:attr:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = attrService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{attrId}")
    @PreAuthorize("hasAuthority('pms:attr:info')")
    public Resp<AttrEntity> info(@PathVariable("attrId") Long attrId) {
        AttrEntity attr = attrService.getById(attrId);

        return Resp.ok(attr);
    }

    /**
     * 保存：可以关联分组保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('pms:attr:save')")
    public Resp<Object> save(@RequestBody AttrVO AttrVO) {
        boolean b = this.attrService.saveAttrAndRelation(AttrVO);
        if (!b) {
            return Resp.fail("添加失败");
        }
        return Resp.ok("添加成功");
    }

    /**
     * 修改：可以关联分组修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('pms:attr:update')")
    public Resp<Object> update(@RequestBody AttrVO attrVO) {
        //this.attrService.updateAttrAndRelation(attrVO);
        attrService.updateById(attrVO);
        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('pms:attr:delete')")
    public Resp<Object> delete(@RequestBody Long[] attrIds) {
        attrService.removeByIds(Arrays.asList(attrIds));

        return Resp.ok(null);
    }

}
