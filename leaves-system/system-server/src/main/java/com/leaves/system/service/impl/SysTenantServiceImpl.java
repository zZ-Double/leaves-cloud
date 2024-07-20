package com.leaves.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaves.system.mapper.SysTenantMapper;
import com.leaves.system.model.entity.SysTenant;
import com.leaves.system.model.param.TenantParam;
import com.leaves.system.service.SysTenantService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 租户信息表 服务实现类
 * </p>
 *
 * @author leaves
 * @since 2024-07-19
 */
@Service
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements SysTenantService {

    @Override
    public Boolean tenantSave(SysTenant tenant) {
        String[] array = tenant.getDateRange().stream().toArray(String[]::new);
        tenant.setBeginDate(LocalDateTime.parse(array[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        tenant.setEndDate(LocalDateTime.parse(array[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return save(tenant);
    }

    @Override
    public Boolean tenantUpdate(SysTenant tenant) {
        SysTenant dbTenant = getById(tenant.getId());
        Assert.isTrue(ObjectUtil.isNotNull(dbTenant), "查询的数据不存在");
        String[] array = tenant.getDateRange().stream().toArray(String[]::new);
        tenant.setBeginDate(LocalDateTime.parse(array[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        tenant.setEndDate(LocalDateTime.parse(array[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return updateById(tenant);
    }

    @Override
    public IPage<SysTenant> tenantPage(TenantParam param) {
        Page<SysTenant> page = new Page<>(param.getCurrent(), param.getSize());
        return this.baseMapper.tenantPage(page, param);
    }

    @Override
    public SysTenant tenantQuery(String id) {
        SysTenant sysTenant = getById(id);
        sysTenant.getDateRange().add(sysTenant.getBeginDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        sysTenant.getDateRange().add(sysTenant.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return sysTenant;
    }
}
