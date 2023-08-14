import request from "@/utils/request";
import { Option, MenuForm, MenuQuery, MenuVO } from "./types"
import { AxiosPromise } from 'axios';
import { sys_base_url } from "..";

/**
 * 获取菜单树形列表
 */
export function listMenus(queryParams: MenuQuery): AxiosPromise<MenuVO[]> {
    return request({
      url: sys_base_url + 'menus/list',
      method: "get",
      params: queryParams,
    });
  }

/**
 * 新增菜单 
 */
export function saveMenu(data: MenuForm) {
    return request({
        url: sys_base_url + 'menus/save',
        method: 'post',
        data
    });
}


/**
 * 加载菜单下拉菜单
 */
export function menuOptions(): AxiosPromise<Option> {
    return request({
        url: sys_base_url + 'menus/options',
        method: 'get',
    });
}