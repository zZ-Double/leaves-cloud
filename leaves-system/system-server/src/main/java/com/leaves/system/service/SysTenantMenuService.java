package com.leaves.system.service;

import com.leaves.system.model.entity.SysTenantMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Leaves
* @description 针对表【sys_tenant_menu(租户和资源关联表)】的数据库操作Service
* @createDate 2024-07-21 09:36:00
*/
public interface SysTenantMenuService extends IService<SysTenantMenu> {

    List<String> listMenuIdsByTenant(String tenantId);

}
