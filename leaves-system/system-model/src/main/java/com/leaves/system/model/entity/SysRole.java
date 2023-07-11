package com.leaves.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.leaves.common.base.BaseEntity;
import lombok.Data;

/**
 * 角色信息表
 * @TableName sys_role
 */
@TableName(value ="sys_role")
@Data
public class SysRole extends BaseEntity implements Serializable {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    private Integer dataScope;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 删除标志
     */
    private Boolean delFlag;

    /**
     * 备注
     */
    private String remark;


}