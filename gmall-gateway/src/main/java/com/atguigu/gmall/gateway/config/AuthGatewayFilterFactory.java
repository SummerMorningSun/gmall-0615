package com.atguigu.gmall.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * @author erdong
 * @create 2019-11-14 16:59
 */
@Component
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Autowired
    private AuthGatewayFilter gatewayFilter;

    @Override
    public GatewayFilter apply(Object config) {

        return gatewayFilter;
    }
}