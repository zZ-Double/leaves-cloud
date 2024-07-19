package com.leaves.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leaves.system.model.entity.SysTenant;
import com.leaves.system.model.param.TenantParam;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 * 租户信息表 Mapper 接口
 * </p>
 *
 * @author leaves
 * @since 2024-07-19
 */
public interface SysTenantMapper extends BaseMapper<SysTenant> {

    IPage<SysTenant> tenantPage(Page<SysTenant> page, @Param("param") TenantParam param);

}
