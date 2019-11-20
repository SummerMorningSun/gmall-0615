package com.atguigu.gmall.search.controller;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.search.service.SearchService;
import com.atguigu.gmall.search.vo.SearchParamVO;
import com.atguigu.gmall.search.vo.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author erdong
 * @create 2019-11-08 20:31
 */
@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public Resp<Object> search(SearchParamVO searchParamVO) {
        SearchResponse search = this.searchService.search(searchParamVO);
        return Resp.ok(search);
    }

    @GetMapping("sys")
    public Resp<Object> query(){
        return Resp.ok("输出打印，请求访问成功");
    }

}
