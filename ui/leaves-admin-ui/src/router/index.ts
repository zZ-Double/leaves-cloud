//通过vue-router插件实现模板路由配置
import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router';

// 定义常量路由
export const constantRoute: Array<RouteRecordRaw> = [
  {
    //登录
    path: '/login',
    component: () => import('@/views/login/index.vue'),
    name: 'login',
    meta: { hidden: true },
  },
  {
    path: "/",
    component: () => import('@/layout/index.vue'),
    redirect: "/dashboard",
    children: [
      {
        path: "dashboard",
        component: () => import("@/views/dashboard/index.vue"),
        name: "Dashboard",
        meta: { title: "首页", icon: "homepage", affix: true },
      },
      {
        path: '/404',
        component: () => import('@/views/404/index.vue'),
        name: '404',
        meta: { hidden: true },
      },
    ],
  },
  {
    path: '/user',
    component: () => import('@/layout/index.vue'),
    meta: { hidden: true },
    redirect: 'noredirect',
    children: [
      {
        path: 'profile',
        component: () => import('@/views/sys/profile/index.vue'),
        name: 'Profile',
        meta: { title: '个人中心', icon: 'user' }
      }
    ]
  }
]

//创建路由器
const router = createRouter({
  //路由模式hash
  history: createWebHashHistory(),
  routes: constantRoute,
  //滚动行为
  scrollBehavior() {
    return {
      left: 0,
      top: 0,
    }
  },
})

// 重置路由
export function resetRouter() {
  router.replace({ path: "/login" });
}

export default router
