//通过vue-router插件实现模板路由配置
import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router';
import useStore from '@/store';

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
    path: '/404',
    component: () => import('@/views/404/index.vue'),
    name: '404',
    meta: { hidden: true },
  },
  {
    //登录成功以后展示数据的路由
    path: '/',
    component: () => import('@/layout/index.vue'),
    name: 'layout',
    meta: { title: '', hidden: false, icon: '' },
    redirect: '/home',
    children: [
      {
        path: '/home',
        component: () => import('@/views/home/index.vue'),
        meta: { title: '首页', hidden: false, icon: 'homepage' }
      }
    ]
  },
  {
    path: '/sys',
    component: () => import('@/layout/index.vue'),
    name: 'Sys',
    meta: {
      title: '系统设置',
      icon: 'system'
    },
    redirect: '/sys/user',
    children: [
      {
        path: '/sys/user',
        component: () => import('@/views/sys/user.vue'),
        name: 'User',
        meta: { title: '用户管理', icon: 'User'}
      },
      {
        path: '/sys/role',
        component: () => import('@/views/sys/role.vue'),
        name: 'Role',
        meta: { title: '角色管理', icon: 'UserFilled'}
      },
      {
        path: '/sys/menu',
        component: () => import('@/views/sys/menu.vue'),
        name: 'Menu',
        meta: { title: '菜单管理', icon: 'Monitor'}
      }
    ]
  }
]

//任意路由
export const anyRoute = {
  //任意路由
  path: '/:pathMatch(.*)*',
  redirect: '/404',
  name: 'Any',
  meta: {
    title: '任意路由',
    hidden: true,
    icon: 'DataLine'
  }
}

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
  const { permission } = useStore();
  permission.routes.forEach((route) => {
    const name = route.name;
    if (name && router.hasRoute(name)) {
      router.removeRoute(name);
    }
  });
}

export default router
