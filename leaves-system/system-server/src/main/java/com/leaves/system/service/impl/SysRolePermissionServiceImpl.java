package com.leaves.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaves.system.mapper.SysRolePermissionMapper;
import com.leaves.system.model.entity.SysRolePermission;
import com.leaves.system.service.SysRolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author leaves
 * @description 针对表【sys_role_permission(角色和权限关联表)】的数据库操作Service实现
 * @createDate 2023-07-11 11:40:10
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission>
        implements SysRolePermissionService {

    @Override
    public Boolean setRolePermissions(String permissionIds, String roleId) {
        boolean flag = false;
        // 更新用户时，如果角色id字符串为空，说明是将用户角色全部去除
        if (StrUtil.isBlank(permissionIds)) {
            return removeRolePermissions(Arrays.asList(roleId));
        }
        List<String> permissionIdList = Arrays.asList(permissionIds.split(","));
        if (!CollectionUtils.isEmpty(permissionIdList)) {
            removeRolePermissions(Arrays.asList(roleId));
            List<SysRolePermission> rolePermissions = new ArrayList<>(permissionIdList.size());
            permissionIdList.forEach(perId -> {
                SysRolePermission sysRolePermission = new SysRolePermission();
                sysRolePermission.setRoleId(roleId);
                sysRolePermission.setPerId(perId);
                rolePermissions.add(sysRolePermission);
            });
            flag = saveBatch(rolePermissions);
        }
        return flag;
    }

    @Override
    public Boolean removeRolePermissions(List<String> roleIds) {
        return remove(new QueryWrapper<SysRolePermission>()
                .lambda()
                .in(ObjectUtil.isNotEmpty(roleIds), SysRolePermission::getRoleId, roleIds));
    }
}




