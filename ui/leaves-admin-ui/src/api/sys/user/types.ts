import { PageQuery, PageResult } from "@/api/types";

/**
 * 登录用户信息
 */
export interface UserInfo {
  nickName: string;
  avatar: string;
  roles: string[];
  perms: string[];
}

/**
 * 用户查询参数
 */
export interface UserQuery extends PageQuery {
  keywords: string;
  status: string;
  deptId: string;
}

/**
 * 用户分页列表项声明
 */
export interface UserType {
  id?: string;
  username: string;
  nickName: string;
  phoneNumber: string;
  sex: string;
  avatar: string;
  email: string;
  status: string;
  deptName: string;
  roleNames: string;
  createTime: string;
  deptId: string;
}

/**
 * 用户分页项类型声明
 */
export type UserPageResult = PageResult<UserType[]>;

/**
 * 用户表单类型声明
 */
export interface UserForm {
  id: string | undefined;
  deptId: string;
  username: string;
  nickName: string;
  phoneNumber: string;
  email: string;
  sex: string;
  status: string;
  remark: string;
  roleIds: [];
  dataScope?: number
}

/**
 * 用户导入表单类型声明
 */
export interface UserImportData {
  deptId: string;
  roleIds: string[];
}
