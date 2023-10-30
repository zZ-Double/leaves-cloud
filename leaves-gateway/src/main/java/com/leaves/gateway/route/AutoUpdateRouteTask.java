package com.leaves.gateway.route;


import lombok.extern.slf4j.Slf4j;

/**
 * 自动更新路由的任务
 */
@Slf4j
public class AutoUpdateRouteTask implements Runnable {

    private long SLEEP_TIME_MILLIS;

    private IRoute route;

    public AutoUpdateRouteTask(IRoute route, int taskRunSecond) {
        this.route = route;
        SLEEP_TIME_MILLIS = taskRunSecond * 1000;
    }

    @Override
    public void run() {
        while (true) {
            log.debug("=============【AutoUpdateRouteTask】开始运行===================");
            route.routesUpdate();
            log.debug("=============【AutoUpdateRouteTask】运行结束===================");
            try {
                Thread.sleep(SLEEP_TIME_MILLIS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
