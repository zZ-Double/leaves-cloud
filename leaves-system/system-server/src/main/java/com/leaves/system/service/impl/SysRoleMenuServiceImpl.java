package com.leaves.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaves.system.model.entity.SysRoleMenu;
import com.leaves.system.service.SysRoleMenuService;
import com.leaves.system.mapper.SysRoleMenuMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author leaves
 * @description 针对表【sys_role_menu(角色和资源关联表)】的数据库操作Service实现
 * @createDate 2023-07-12 19:39:59
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Override
    public List<String> listMenuIdsByRoleId(String roleId) {
        return this.baseMapper.listMenuIdsByRoleId(roleId);
    }
}




