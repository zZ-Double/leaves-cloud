package com.leaves.system.model.vo;

import com.leaves.system.model.entity.SysUser;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author leaves
 * @version 1.0
 * @description
 * @date 2023/7/12
 */
@Setter
@Getter
public class UserVO extends SysUser {

    /**
     * 角色ID集合
     */
    private Set<String> roles;

    /**
     * 权限编码集合
     */
    private Set<String> perms;

    /**
     * 数据权限范围
     */
    private Integer dataScope;
}
