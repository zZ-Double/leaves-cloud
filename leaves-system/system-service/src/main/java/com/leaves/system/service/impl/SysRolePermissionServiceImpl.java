package com.leaves.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaves.system.model.entity.SysRolePermission;
import com.leaves.system.service.SysRolePermissionService;
import com.leaves.system.mapper.SysRolePermissionMapper;
import org.springframework.stereotype.Service;

/**
* @author leaves
* @description 针对表【sys_role_permission(角色和权限关联表)】的数据库操作Service实现
* @createDate 2023-07-11 11:40:10
*/
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission>
    implements SysRolePermissionService{

}




