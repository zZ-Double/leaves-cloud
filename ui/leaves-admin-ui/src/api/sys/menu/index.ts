import request from "@/utils/request";
import { Option } from "./types"
import { AxiosPromise } from 'axios';
import { sys_base_url } from "..";
import { get, method } from "lodash";

export function menuOptionsList(): AxiosPromise<Option> {
    return request({
        url: sys_base_url + 'menus/options/list',
        method: 'get',
      });
}