package com.leaves.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leaves.system.model.entity.SysTenant;
import com.leaves.system.model.param.TenantParam;


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

}
