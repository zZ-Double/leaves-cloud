import { defineStore } from 'pinia'
import { PermissionState } from './types';
import { RouteRecordRaw } from 'vue-router';
import { constantRoute } from '@/router';
import { listRoutes } from '@/api/sys/menu';

const usePermissionStore = defineStore({
  id: 'permission',
  state: (): PermissionState => ({
    routes: constantRoute,
    addRoutes: [],
  }),
  actions: {

    setRoutes(routes: RouteRecordRaw[]) {
      this.addRoutes = routes;
      this.routes = constantRoute.concat(routes);
    },

    generateRoutes(roles: string[]) {
      return new Promise((resolve, reject) => {
        listRoutes()
          .then((response) => {
            const asyncRoutes = response.data;
            const accessedRoutes = filterAsyncRoutes(asyncRoutes, roles);
            this.setRoutes(accessedRoutes);
            resolve(accessedRoutes);
          })
          .catch((error) => {
            reject(error);
          });
      });
    },
  }
})

/**
 * Use meta.role to determine if the current user has permission
 *
 * @param roles 用户角色集合
 * @param route 路由
 * @returns
 */
const hasPermission = (roles: string[], route: RouteRecordRaw) => {
  if (route.meta && route.meta.roles) {
    // 角色【超级管理员】拥有所有权限，忽略校验
    if (roles.includes("ROOT")) {
      return true;
    }
    return roles.some((role) => {
      if (route.meta?.roles !== undefined) {
        return (route.meta.roles as string[]).includes(role);
      }
    });
  }
  return false;
};

const modules = import.meta.glob("../../views/**/**.vue");
const Layout = () => import("@/layout/index.vue");


/**
 * 递归过滤有权限的异步(动态)路由
 *
 * @param routes 接口返回的异步(动态)路由
 * @param roles 用户角色集合
 * @returns 返回用户有权限的异步(动态)路由
 */
const filterAsyncRoutes = (routes: RouteRecordRaw[], roles: string[]) => {
  const asyncRoutes: RouteRecordRaw[] = [];

  routes.forEach((route) => {
    const tmpRoute = { ...route }; // ES6扩展运算符复制新对象

    // 判断用户(角色)是否有该路由的访问权限
    if (hasPermission(roles, tmpRoute)) {
      if (tmpRoute.component?.toString() == "Layout") {
        tmpRoute.component = Layout;
        console.log();
      } else {
        const component = modules[`../../views/${tmpRoute.component}.vue`];
        if (component) {
          tmpRoute.component = component;
        } else {
          tmpRoute.component = modules[`../../views/404/index.vue`];
        }
      }

      if (tmpRoute.children) {
        tmpRoute.children = filterAsyncRoutes(tmpRoute.children, roles);
      }

      asyncRoutes.push(tmpRoute);
    }
  });

  return asyncRoutes;
};

export default usePermissionStore;