package com.leaves.system.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户和角色关联表
 *
 * @TableName sys_user_role
 */
@TableName(value = "sys_user_role")
@Data
public class SysUserRole implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 角色ID
     */
    private String roleId;

    @TableLogic
    private Boolean deleted;

}