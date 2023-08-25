import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { UserForm, UserInfo, UserPageResult, UserQuery } from './types';
import { sys_base_url } from '..';

/**
 * 登录成功后获取用户信息（昵称、头像、权限集合和角色集合）
 */
export function getUserInfo(): AxiosPromise<UserInfo> {
  return request({
    url: sys_base_url + 'user/me',
    method: 'get',
  });
}

/**
 * 获取用户分页列表
 *
 * @param queryParams
 */
export function userPages(queryParams: UserQuery): AxiosPromise<UserPageResult> {
  return request({
    url: sys_base_url + 'user/page',
    method: 'get',
    params: queryParams,
  });
}

/**
 * 获取用户表单详情
 *
 * @param userId
 */
export function getUser(userId: string): AxiosPromise<UserForm> {
  return request({
    url: sys_base_url + 'user/query/' + userId,
    method: 'get',
  });
}

/**
 * 添加用户
 *
 * @param data
 */
export function saveUser(data: any) {
  return request({
    url: sys_base_url + 'user/save',
    method: 'post',
    data: data,
  });
}

/**
 * 修改用户
 *
 * @param id
 * @param data
 */
export function updateUser(data: UserForm) {
  return request({
    url: sys_base_url + 'user/update',
    method: 'post',
    data: data,
  });
}

/**
 * 重置用户密码 
 */
export function resetPasswd(id:string) {
  return request({
    url: sys_base_url + 'user/passwd/reset/' + id,
    method: 'get'
  })
}
/**
 * 修改用户密码
 *
 * @param id
 * @param password
 */
export function updateUserPassword(id: number, password: string) {
  return request({
    url: sys_base_url + 'user/' + id + '/password',
    method: 'patch',
    params: { password: password },
  });
}

/**
 * 删除用户
 *
 * @param ids
 */
export function removeUser(ids: string) {
  return request({
    url: sys_base_url + 'user/remove/' + ids,
    method: 'get',
  });
}

// /**
//  * 下载用户导入模板
//  *
//  * @returns
//  */
// export function downloadTemplate() {
//   return request({
//     url: '/leaves-system/api/v1/user//template',
//     method: 'get',
//     responseType: 'arraybuffer',
//   });
// }

// /**
//  * 导出用户
//  *
//  * @param queryParams
//  * @returns
//  */
// export function exportUser(queryParams: UserQuery) {
//   return request({
//     url: '/leaves-system/api/v1/user//_export',
//     method: 'get',
//     params: queryParams,
//     responseType: 'arraybuffer',
//   });
// }

// /**
//  * 导入用户
//  *
//  * @param file
//  */
// export function importUser(deptId: number, roleIds: string, file: File) {
//   const formData = new FormData();
//   formData.append('file', file);
//   formData.append('deptId', deptId.toString());
//   formData.append('roleIds', roleIds);
//   return request({
//     url: '/leaves-system/api/v1/user//_import',
//     method: 'post',
//     data: formData,
//     headers: {
//       'Content-Type': 'multipart/form-data',
//     },
//   });
// }
