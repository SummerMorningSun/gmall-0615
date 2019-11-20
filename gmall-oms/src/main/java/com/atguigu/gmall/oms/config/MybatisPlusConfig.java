package com.atguigu.gmall.oms.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分页的拦截器
 *
 * @author erdong
 * @create 2019-11-01 6:20
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //  设置请求的页面大于最大页后操作，true调回到首页，false 继续请求  默认false
//        paginationInterceptor.setOverflow(false);
        //  设置最大单页限制数量，默认500条，-1 不受限制
//        paginationInterceptor.setLimit(500);
        return paginationInterceptor;
    }

}
