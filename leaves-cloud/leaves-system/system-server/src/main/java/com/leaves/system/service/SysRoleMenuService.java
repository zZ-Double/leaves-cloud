package com.leaves.system.service;

import com.leaves.system.model.entity.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author leaves
* @description 针对表【sys_role_menu(角色和资源关联表)】的数据库操作Service
* @createDate 2023-07-12 19:39:59
*/
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 获取角色拥有的菜单ID集合
     *
     * @param roleId
     * @return
     */
    List<String> listMenuIdsByRoleId(String roleId);
}
