package com.atguigu.gmall.search.service;


import com.atguigu.gmall.search.vo.SearchParamVO;
import com.atguigu.gmall.search.vo.SearchResponse;

/**
 * @author erdong
 * @create 2019-11-08 20:35
 */
public interface SearchService {

    public SearchResponse search(SearchParamVO searchParamVO);

}
