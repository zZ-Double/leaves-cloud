package com.leaves.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaves.system.model.entity.SysUserRole;
import com.leaves.system.service.SysUserRoleService;
import com.leaves.system.mapper.SysUserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author leaves
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2023-07-11 11:40:10
*/
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole>
    implements SysUserRoleService{

}




