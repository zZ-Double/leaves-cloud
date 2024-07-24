package com.leaves.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaves.common.constant.GlobalConstants;
import com.leaves.common.model.Option;
import com.leaves.common.security.utils.SecurityUtils;
import com.leaves.system.mapper.SysTenantMapper;
import com.leaves.system.model.entity.SysRole;
import com.leaves.system.model.entity.SysTenant;
import com.leaves.system.model.entity.SysTenantMenu;
import com.leaves.system.model.param.TenantParam;
import com.leaves.system.service.SysTenantMenuService;
import com.leaves.system.service.SysTenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 租户信息表 服务实现类
 * </p>
 *
 * @author leaves
 * @since 2024-07-19
 */
@Service
@RequiredArgsConstructor
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements SysTenantService {

    private final SysTenantMenuService tenantMenuService;

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

    @Override
    public List<String> getTenantMenuIds(String tenantId) {
        return tenantMenuService.listMenuIdsByTenant(tenantId);
    }

    @Override
    public Boolean updateTenantMenus(String tenantId, List<String> menuIds) {
        // 删除租户菜单
        boolean flag = tenantMenuService.remove(new LambdaQueryWrapper<SysTenantMenu>().eq(SysTenantMenu::getTenantId, tenantId));
        // 新增租户菜单
        if (CollectionUtil.isNotEmpty(menuIds)) {
            List<SysTenantMenu> roleMenus = menuIds.stream()
                    .map(menuId -> new SysTenantMenu(tenantId, menuId))
                    .collect(Collectors.toList());
            flag = tenantMenuService.saveBatch(roleMenus);
        }
        return flag;
    }

    @Override
    public List<Option> tenantOptions(String tenantId) {
        // 查询数据
        List<SysTenant> tenantList = this.list(new LambdaQueryWrapper<SysTenant>()
                .eq(!SecurityUtils.isRoot() && StrUtil.isNotBlank(tenantId),
                        SysTenant::getId, tenantId)
                .select(SysTenant::getId, SysTenant::getTenantName)
                .orderByAsc(SysTenant::getCreateTime));

        if (CollectionUtil.isEmpty(tenantList)) {
            return Collections.EMPTY_LIST;
        }

        return tenantList.stream().map(r -> {
            Option option = new Option();
            option.setLabel(r.getTenantName());
            option.setValue(r.getId());
            return option;
        }).collect(Collectors.toList());
    }
}
