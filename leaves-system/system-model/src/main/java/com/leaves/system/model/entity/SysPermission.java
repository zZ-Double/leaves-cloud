package com.leaves.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.leaves.common.base.BaseEntity;
import lombok.Data;

/**
 * 权限表
 *
 * @TableName sys_permission
 */
@TableName(value = "sys_permission")
@Data
public class SysPermission extends BaseEntity {


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
     * 备注
     */
    private String remark;


}