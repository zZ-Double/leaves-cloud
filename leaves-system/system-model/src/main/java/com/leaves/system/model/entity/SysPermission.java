package com.leaves.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.leaves.common.base.BaseEntity;
import lombok.Data;

/**
 * 权限表
 * @TableName sys_permission
 */
@TableName(value ="sys_permission")
@Data
public class SysPermission extends BaseEntity implements Serializable {
    /**
     * 权限ID
     */
    @TableId
    private String id;

    /**
     * 权限名称
     */
    private String perName;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 权限标识
     */
    private String perCode;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 删除标志
     */
    private Integer delFlag;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新者
     */
    private String updateUser;

    /**
     * 备注
     */
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}