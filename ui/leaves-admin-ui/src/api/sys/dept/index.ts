import request from "@/utils/request";
import { DeptVO, Query } from "./types"
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