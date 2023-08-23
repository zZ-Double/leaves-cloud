package com.leaves.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.leaves.common.base.BaseEntity;
import com.leaves.common.enums.DataScopeEnum;
import com.leaves.common.enums.GenderEnum;
import com.leaves.common.enums.StatusEnum;
import lombok.Data;

/**
 * 用户信息表
 *
 * @TableName sys_user
 */
@TableName(value = "sys_user")
@Data
public class SysUser extends BaseEntity {

    /**
     * 登录账号
     */
    private String username;

    /**
     * 密码
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户类型（00系统用户 01注册用户）
     */
    private String userType;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 个人介绍
     */
    private String description;

    /**
     * 邮箱验证标志
     */
    private Integer emailFlag;

    /**
     * 手机验证标志
     */
    private Integer phoneFlag;

    /**
     * 用户性别（0男 1女 2未知）
     */
    private GenderEnum sex;

    /**
     * 头像路径
     */
    private String avatar;

    /**
     * 部门id
     */
    private String deptId;

    /**
     * 状态
     */
    private StatusEnum status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 数据范围（1：全部数据权限 2：本部门及以下数据权限 3：本部门数据权限 4：本人数据）
     */
    private Integer dataScope;

}