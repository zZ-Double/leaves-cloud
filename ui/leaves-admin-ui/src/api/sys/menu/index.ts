import request from "@/utils/request";
import { Option, MenuForm, MenuQuery, MenuVO } from "./types"
import { AxiosPromise } from 'axios';
import { sys_base_url } from "..";

/**
 * 获取菜单树形列表
 */
export function listMenus(queryParams: MenuQuery): AxiosPromise<MenuVO[]> {
    return request({
      url: sys_base_url + 'menu/list',
      method: "get",
      params: queryParams,
    });
  }

/**
 * 新增菜单 
 */
export function saveMenu(data: MenuForm) {
    return request({
        url: sys_base_url + 'menu/save',
        method: 'post',
        data
    });
}

/**
 * 修改菜单 
 */
export function updateMenu(data: MenuForm) {
    return request({
        url: sys_base_url + 'menu/update',
        method: 'post',
        data
    });
}

/**
 * 菜单详情
 */
export function getMenu(id: string): AxiosPromise<MenuForm> {
    return request({
        url: sys_base_url + 'menu/query/' + id,
        method: 'get',
    });
}

/**
 * 删除菜单
 */
export function removeMenu(ids: string) {
    return request({
        url: sys_base_url + 'menu/remove/' + ids,
        method: 'get',
    });
}

/**
 * 加载下拉列表
 */
export function menuOptions(): AxiosPromise<Option> {
    return request({
        url: sys_base_url + 'menu/options',
        method: 'get',
    });
}

/**
 * 加载菜单路由列表
 */
export function listRoutes() {
    return request({
        url: sys_base_url + 'menu/routes',
        method: 'get',
    });
}