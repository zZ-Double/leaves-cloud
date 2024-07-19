package com.leaves.gateway.route;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 初始化自动更新路由的监听
 */
@Component
public class InitAutoUpdateRouteListener implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String nacosServerAddr;

    @Value("${spring.cloud.nacos.discovery.autoUpdateRoute.enable:true}")
    private boolean isAutoUpdateRoute;

    @Value("${spring.cloud.nacos.discovery.autoUpdateRoute.runSecond:10}")
    private int taskRunSecond;

    @Autowired
    private IRoute route;

    private ExecutorService executor;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (StrUtil.isNotBlank(nacosServerAddr)) {
            route.routesUpdate();
            if (isAutoUpdateRoute) {
                if (taskRunSecond < 1) {
                    taskRunSecond = 30;
                }
                initAutoUpdateRoute(new AutoUpdateRouteTask(route, taskRunSecond));
            }
        }
    }

    private void initAutoUpdateRoute(Runnable task) {
        executor = Executors.newSingleThreadExecutor(r -> {
            Thread thread = new Thread(r, "autoUpdateRouteTasks");
            thread.setDaemon(true);
            return thread;
        });
        executor.execute(task);
    }

}
