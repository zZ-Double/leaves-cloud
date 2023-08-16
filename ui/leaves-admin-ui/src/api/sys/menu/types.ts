/**
 * Option
 */
export interface Option {
    children?: Option[];
    /**
     * 选项的标签
     */
    label: string;
    /**
     * 选项的值
     */
    value: string;
}

/**
 * SysMenu
 */
export interface MenuForm {

    id?: string;

    /**
     * 组件路径(vue页面完整路径，省略.vue后缀)
     */
    component?: string;
    /**
     * 删除标志
     */
    delFlag?: boolean;
    /**
     * 菜单图标
     */
    icon?: string;
    /**
     * 菜单名称
     */
    name?: string;
    /**
     * 父菜单ID
     */
    parentId?: string;
    /**
     * 路由路径(浏览器地址栏路径)
     */
    path?: string;
    /**
     * 按钮权限标识
     */
    perm?: string;
    /**
     * 跳转路径
     */
    redirect?: string;
    /**
     * 备注
     */
    remark?: string;
    /**
     * 排序
     */
    sort?: number;
    /**
     * 状态（1正常 0停用）
     */
    status?: string;
    /**
     * 菜单类型(MENU：菜单；CATALOG：目录；EXT_LINK：外链；BUTTON：按钮)
     */
    type?: string;
}

export interface MenuQuery {
    /**
     * 关键字(菜单名称)
     */
    keywords?: string;
}


export interface MenuVO {
    children?: MenuVO[];
    component?: string;
    createTime?: string;
    icon?: string;
    id?: string;
    name?: string;
    parentId?: string;
    /**
     * 按钮权限标识
     */
    perm?: string;
    redirect?: string;
    path?: string;
    sort?: number;
    /**
     * 菜单类型
     */
    type?: string;
    updateTime?: string;
    status?: number;
}

