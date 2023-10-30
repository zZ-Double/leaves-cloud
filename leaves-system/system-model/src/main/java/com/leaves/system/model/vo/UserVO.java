package com.leaves.system.model.vo;

import com.leaves.system.model.entity.SysUser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Setter
@Getter
public class UserVO extends SysUser {

    /**
     * 角色编码集合
     */
    private Set<String> roles;

    /**
     * 权限编码集合
     */
    private Set<String> perms;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 角色名称
     */
    private String roleNames;

    /**
     * 角色ID集合
     */
    private List<String> roleIds;
}
