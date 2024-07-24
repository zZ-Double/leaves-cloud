package com.leaves.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leaves.common.model.Option;
import com.leaves.system.model.entity.SysTenant;
import com.leaves.system.model.param.TenantParam;

import java.util.List;


/**
 * <p>
 * 租户信息表 服务类
 * </p>
 *
 * @author leaves
 * @since 2024-07-19
 */
public interface SysTenantService extends IService<SysTenant> {

    Boolean tenantSave(SysTenant tenant);

    Boolean tenantUpdate(SysTenant tenant);

    IPage<SysTenant> tenantPage(TenantParam param);

    SysTenant tenantQuery(String id);

    /**
     * 获取租户的资源ID集合,资源包括菜单和权限
     *
     * @param tenantId
     * @return
     */
    List<String> getTenantMenuIds(String tenantId);

    /**
     * 修改租户的资源权限
     *
     * @param tenantId
     * @param menuIds
     * @return
     */
    Boolean updateTenantMenus(String tenantId, List<String> menuIds);

    List<Option> tenantOptions(String tenantId);

}
