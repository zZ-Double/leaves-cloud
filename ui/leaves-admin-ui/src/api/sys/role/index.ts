import request from '@/utils/request'
import { AxiosPromise } from 'axios'
import { Query, RolePageResult, RoleVO } from './types'
import { sys_base_url } from '..'
import { Option } from '../menu/types'


export function pageRoles(queryParams: Query): AxiosPromise<RolePageResult> {
    return request({
        url: sys_base_url + 'role/page',
        method: 'get',
        params: queryParams
    })
}

export function saveRole(data: RoleVO) {
    return request({
        url: sys_base_url + 'role/save',
        method: 'post',
        data
    })
}

export function updateRole(data: RoleVO) {
    return request({
        url: sys_base_url + 'role/update',
        method: 'post',
        data
    })
}

export function removeRole(ids: string) {
    return request({
        url: sys_base_url + 'role/remove/' + ids,
        method: 'get',
    });
}

export function getRole(id: string): AxiosPromise<RoleVO> {
    return request({
        url: sys_base_url + 'role/query/' + id,
        method: 'get',
    });
}

export function getRoleMenuIds(roleId: string): AxiosPromise<string[]> {
    return request({
        url: sys_base_url + 'role/menus/' + roleId,
        method: 'get',
    });
}

export function updateRoleMenus(roleId: string, data: string): AxiosPromise<any> {
    return request({
      url: sys_base_url + 'role/menus/' + roleId,
      method: 'post',
      data: data,
    });
}

export function roleOptions():AxiosPromise<Option[]> {
    return request({
        url: sys_base_url + 'role/options',
        method: 'get'
    })
}
