//通过vue-router插件实现模板路由配置
import { createRouter, createWebHashHistory } from 'vue-router';

// 定义常量路由
export const constantRoute = [
    {
        //登录
        path: '/login',
        component: () => import('@/views/login/index.vue'),
        name: 'login'
    },
    {
        
        path: '/404',
        component: () => import('@/views/404/index.vue'),
        name: '404'
    }
]



//创建路由器
let router = createRouter({
    //路由模式hash
    history: createWebHashHistory(),
    routes: constantRoute,
    //滚动行为
    scrollBehavior() {
        return {
            left: 0,
            top: 0
        }
    }
});


export default router;