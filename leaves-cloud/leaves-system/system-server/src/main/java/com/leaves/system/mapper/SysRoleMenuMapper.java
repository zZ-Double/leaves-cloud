package com.leaves.system.mapper;

import com.leaves.system.model.entity.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author leaves
* @description 针对表【sys_role_menu(角色和资源关联表)】的数据库操作Mapper
* @createDate 2023-07-12 19:39:59
* @Entity com.leaves.system.model.entity.SysRoleMenu
*/
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    @Select("SELECT rm.menu_id FROM sys_role_menu rm INNER JOIN sys_menu m ON rm.menu_id = m.id WHERE rm.role_id = #{roleId}")
    List<String> listMenuIdsByRoleId(String roleId);

}




