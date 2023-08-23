package com.leaves.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import com.leaves.common.base.BaseEntity;
import com.leaves.common.enums.StatusEnum;
import lombok.Data;

/**
 * 角色信息表
 * @TableName sys_role
 */
@TableName(value ="sys_role")
@Data
public class SysRole extends BaseEntity {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 数据范围（1：全部数据权限 2：本部门及以下数据权限 3：本部门数据权限 4：本人数据）
     */
    private Integer dataScope;

    /**
     * 状态
     */
    private StatusEnum status;

    /**
     * 备注
     */
    private String remark;


}