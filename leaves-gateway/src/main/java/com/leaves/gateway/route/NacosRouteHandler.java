package com.leaves.gateway.route;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * nacos 路由更新
 */
@Slf4j
@Component
public class NacosRouteHandler implements IRoute {

    @Value("${spring.application.name}")
    private String myServiceName;

    private final DiscoveryClient discoveryClient;

    private final DynamicRouteService dynamicRouteService;

    private Map<String, RouteDefinition> cachedRouteMap = new HashMap<>();

    public NacosRouteHandler(DiscoveryClient discoveryClient, DynamicRouteService dynamicRouteService) {
        this.discoveryClient = discoveryClient;
        this.dynamicRouteService = dynamicRouteService;
    }

    /**
     * 从nacos中获取服务并更新路由
     */
    @Override
    public void routesUpdate() {
        // 从nacos命名空间中获取到的所有服务名称，这其中还包含了自己的服务名
        List<String> serviceNames = discoveryClient.getServices();
        Map<String, RouteDefinition> toAddRouteMap = new HashMap<>();
        Map<String, String> serviceNamesMap = new HashMap<>();
        Map<String, RouteDefinition> toRemoveRouteMap = new HashMap<>();
        // 服务列表和缓存中的路由对比,找出新的服务待添加
        for (String serviceName : serviceNames) {
            if (!serviceName.equals(myServiceName)) {

                List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
                if (instances.size() < 1) {
                    continue;
                }

                if (cachedRouteMap.get(serviceName) == null) {
                    RouteDefinition routeDefinition = new RouteDefinition();
                    routeDefinition.setId(UUID.randomUUID().toString());
                    routeDefinition.setUri(URI.create("lb://" + serviceName));
                    PredicateDefinition predicateDefinition = new PredicateDefinition();
                    predicateDefinition.setName("Path");
                    predicateDefinition.addArg("pattern", "/" + serviceName + "/**");
                    routeDefinition.getPredicates().add(predicateDefinition);
                    FilterDefinition filterDefinition = new FilterDefinition();
                    filterDefinition.setName("TokenRelay");
                    routeDefinition.getFilters().add(filterDefinition);
                    toAddRouteMap.put(serviceName, routeDefinition);
                }
                // 将serviceName加到临时map中,以方便下面查找需要移除的
                serviceNamesMap.put(serviceName, "1");
            }
        }
        // 缓存中的路由和服务列表对比,找出需要移除的
        cachedRouteMap.forEach((key, value) -> {
            if (serviceNamesMap.get(key) == null) {
                toRemoveRouteMap.put(key, value);
            }
        });

        // 将待添加的加到路由和缓存中
        dynamicRouteService.addRoute(toAddRouteMap);
        cachedRouteMap.putAll(toAddRouteMap);


        // 将待移除的从路由和缓存中移除
        toRemoveRouteMap.forEach((key, value) -> {
            dynamicRouteService.deleteRoute(value.getId());
            cachedRouteMap.remove(key);
            log.info("-------服务【{}】已从路由中移除", key);
        });
    }
}

