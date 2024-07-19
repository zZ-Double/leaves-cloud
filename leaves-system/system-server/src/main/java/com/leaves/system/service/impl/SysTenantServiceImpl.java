package com.leaves.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaves.system.mapper.SysTenantMapper;
import com.leaves.system.model.entity.SysTenant;
import com.leaves.system.model.param.TenantParam;
import com.leaves.system.service.SysTenantService;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
    public IPage<SysTenant> tenantPage(TenantParam param) {
        Page<SysTenant> page = new Page<>(param.getCurrent(), param.getSize());
        return this.baseMapper.tenantPage(page, param);
    }
}
