import request from "@/utils/request";
import { DeptVO, Query, DeptForm } from "./types"
import { AxiosPromise } from 'axios';
import { sys_base_url } from "..";
import { Option } from "../menu/types";

/**
 * 列表查询
 */
export function listDepts(queryParams: Query): AxiosPromise<DeptVO[]> {
    return request({
        url: sys_base_url + 'dept/list',
        method: 'get',
        params: queryParams
    })
}

/**
 * 下拉列表
 */
export function deptOptions(): AxiosPromise<Option> {
    return request({
        url: sys_base_url + 'dept/options',
        method: 'get'
    })
}

/**
 * 新增部门
 */
export function saveDept(data: DeptForm) {
    return request({
        url: sys_base_url + 'dept/save',
        method: 'post',
        data
    });
}

/**
 * 修改部门
 */
export function updateDept(data: DeptForm) {
    return request({
        url: sys_base_url + 'dept/update',
        method: 'post',
        data
    });
}

/**
 * 部门详情
 */
export function getDept(id: string): AxiosPromise<DeptForm> {
    return request({
        url: sys_base_url + 'dept/query/' + id,
        method: 'get',
    });
}

/**
 * 删除部门
 */
export function removeDept(ids: string) {
    return request({
        url: sys_base_url + 'dept/remove/' + ids,
        method: 'get',
    });
}