import { defineStore } from 'pinia'
import { PermissionState } from './types';
import { RouteRecordRaw } from 'vue-router';
import { constantRoute } from '@/router';

const usePermissionStore = defineStore({
    id: 'permission',
    state: (): PermissionState => ({
        routes: constantRoute,
        addRoutes: [],
    }),
    actions: {
        setRoutes(routes: RouteRecordRaw[]) {
            this.addRoutes = routes;
            this.routes = constantRoute;
          },
          generateRoutes(roles: string[]) {
            return constantRoute
            // return new Promise((resolve, reject) => {
            //   listRoutes()
            //     .then((response) => {
            //       const asyncRoutes = response.data;
            //       const accessedRoutes = filterAsyncRoutes(asyncRoutes, roles);
            //       this.setRoutes(accessedRoutes);
            //       resolve(accessedRoutes);
            //     })
            //     .catch((error) => {
            //       reject(error);
            //     });
            // });
          },
    }
})

export default usePermissionStore;