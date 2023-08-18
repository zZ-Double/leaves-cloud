/**
 * DeptVO
 */
export interface DeptVO {
    /**
     * 祖级列表
     */
    ancestors?: string;
    createTime?: string;
    createUser?: string;
    /**
     * 删除标志
     */
    delFlag?: boolean;
    /**
     * 部门名称
     */
    deptName?: string;
    /**
     * 邮箱
     */
    email?: string;
    id?: string;
    /**
     * 负责人
     */
    leader?: string;
    /**
     * 显示顺序
     */
    orderNum?: number;
    /**
     * 父部门id
     */
    parentId?: string;
    /**
     * 联系电话
     */
    phone?: string;
    /**
     * 部门状态
     */
    status?: string;
    updateTime?: string;
    updateUser?: string;
}

export interface Query {
    /**
     * 关键字(菜单名称)
     */
    keywords?: string;
}