package com.leaves.system.mapper;

import com.leaves.system.model.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

/**
* @author leaves
* @description 针对表【sys_menu(菜单管理)】的数据库操作Mapper
* @createDate 2023-07-12 19:39:43
* @Entity com.leaves.system.model.entity.SysMenu
*/
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 用户ID获取角色权限集合
     *
     * @param userId
     * @return
     */
    Set<String> listRolePerms(String userId);
}




