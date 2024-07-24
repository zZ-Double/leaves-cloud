package com.leaves.system.mapper;

import com.leaves.system.model.entity.SysTenantMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author Leaves
* @description 针对表【sys_tenant_menu(租户和资源关联表)】的数据库操作Mapper
* @createDate 2024-07-21 09:36:00
* @Entity com.leaves.system.model.entity.SysTenantMenu
*/
public interface SysTenantMenuMapper extends BaseMapper<SysTenantMenu> {

    @Select("SELECT tm.menu_id FROM sys_tenant_menu tm INNER JOIN sys_menu m ON tm.menu_id = m.id WHERE tm.tenant_id = #{tenantId}")
    List<String> listMenuIdsByTenantId(String tenantId);

}




