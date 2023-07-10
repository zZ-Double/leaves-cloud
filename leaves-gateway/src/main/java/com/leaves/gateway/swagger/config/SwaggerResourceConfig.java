package com.leaves.gateway.swagger.config;

import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Primary
@RequiredArgsConstructor
@Slf4j
public class SwaggerResourceConfig implements SwaggerResourcesProvider {

    private final RouteDefinitionRepository routeDefinitionRepository;

    @Override
    public List<SwaggerResource> get() {

        //接口资源列表
        List<SwaggerResource> resources = new ArrayList<>();
        //服务名称列表
        Set<String> routeHosts = new HashSet<>();
        // 去重，多负载服务只添加一次
        Set<String> existsServer = new HashSet<>();

        Flux<RouteDefinition> routeDefinitions = routeDefinitionRepository.getRouteDefinitions();
        routeDefinitions.subscribe(routeDefinition -> {
            List<PredicateDefinition> predicates = routeDefinition.getPredicates();
            if (CollectionUtil.isNotEmpty(predicates)) {
                String host = routeDefinition.getUri().getHost();
                routeHosts.add(host);
                String url = "/" + host + "/v2/api-docs";
                if (!existsServer.contains(url) && !host.contains("dubbo")) {
                    existsServer.add(url);
                    resources.add(swaggerResource(host, url));
                }

            }
        });
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        log.info("name:{},location:{}", name, location);
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }

}
