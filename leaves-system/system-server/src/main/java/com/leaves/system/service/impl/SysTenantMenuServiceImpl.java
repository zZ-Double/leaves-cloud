package com.leaves.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaves.system.model.entity.SysTenantMenu;
import com.leaves.system.service.SysRoleMenuService;
import com.leaves.system.service.SysTenantMenuService;
import com.leaves.system.mapper.SysTenantMenuMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Leaves
 * @description 针对表【sys_tenant_menu(租户和资源关联表)】的数据库操作Service实现
 * @createDate 2024-07-21 09:36:00
 */
@Service
public class SysTenantMenuServiceImpl extends ServiceImpl<SysTenantMenuMapper, SysTenantMenu>
        implements SysTenantMenuService {

    @Override
    public List<String> listMenuIdsByTenant(String tenantId) {
        return this.baseMapper.listMenuIdsByTenantId(tenantId);
    }
}




