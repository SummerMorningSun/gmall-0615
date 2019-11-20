package com.atguigu.gmall.wms.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author erdong
 * @create 2019-11-01 19:22
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean("仓管平台")
    public Docket userApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("仓管平台")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))   // 所有标了api注解的才在文档中展示
                .paths(PathSelectors.regex("/wms.*")) // wms下的所有请求
                .build()
                .apiInfo(apiInfo())
                .enable(true);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("谷粒商城-仓管平台接口文档")
                .description("提供仓管平台的文档")
                .termsOfServiceUrl("http://www.atguigu.com/")
                .version("1.0")
                .build();
    }


}
