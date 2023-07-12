package com.leaves.system.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色和权限关联表
 *
 * @TableName sys_role_permission
 */
@TableName(value = "sys_role_permission")
@Data
public class SysRolePermission implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 权限ID
     */
    private String perId;

    @TableLogic
    private Boolean delFlag;


}