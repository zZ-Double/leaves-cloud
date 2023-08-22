import { PageQuery, PageResult } from "@/api/types";

/**
 * 角色分页
 */
export type RolePageResult = PageResult<RoleVO[]>;

export interface RoleVO {
    /**
     * 角色ID
     */
    id?: string;
    /**
     * 角色编码
     */
    roleCode: string;
    /**
     * 角色名称
     */
    roleName: string;
    /**
     * 角色状态
     */
    status?: string;
    /**
     * 数据权限
     */
    dataScope?: number;
}

export interface Query extends PageQuery {
    /**
     * 关键字(菜单名称)
     */
    keywords?: string;
}