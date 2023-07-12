package com.leaves.system.service;

import com.leaves.system.model.entity.SysRolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author leaves
* @description 针对表【sys_role_permission(角色和权限关联表)】的数据库操作Service
* @createDate 2023-07-11 11:40:10
*/
public interface SysRolePermissionService extends IService<SysRolePermission> {

    /**
     * 角色权限关联关系
     * @param permissionIds 权限id集合
     * @param roleId 角色Id
     * @return
     */
    Boolean setRolePermissions(String permissionIds, String roleId);

    /**
     * 移除角色权限关联关系
     * @param roleIds
     * @return
     */
    Boolean removeRolePermissions(List<String> roleIds);
}
