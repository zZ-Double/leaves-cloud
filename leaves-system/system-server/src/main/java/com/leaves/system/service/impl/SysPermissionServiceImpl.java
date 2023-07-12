package com.leaves.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaves.system.mapper.SysPermissionMapper;
import com.leaves.system.model.entity.SysPermission;
import com.leaves.system.model.param.PermParam;
import com.leaves.system.service.SysPermissionService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author leaves
 * @description 针对表【sys_permission(权限表)】的数据库操作Service实现
 * @createDate 2023-07-11 11:40:10
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission>
        implements SysPermissionService {

    @Override
    public Boolean savePerm(PermParam param) {
        SysPermission dbSysPerm = this.baseMapper.selectOne(new QueryWrapper<SysPermission>()
                .lambda()
                .eq(SysPermission::getPerName, param.getPerName())
                .or()
                .eq(SysPermission::getPerCode, param.getPerCode()));
        Assert.isTrue(ObjectUtil.isNotNull(dbSysPerm), "资源名称" + param.getPerName()
                + "或资源CODE" + param.getPerCode() + "已存在");

        SysPermission sysPermission = new SysPermission();
        BeanUtil.copyProperties(dbSysPerm, sysPermission, true);
        return this.baseMapper.insert(sysPermission) > 0;
    }

    @Override
    public Boolean removePerm(String ids) {
        Assert.isTrue(StrUtil.isBlank(ids), "删除的数据不存在");
        List<String> permIds = Arrays.asList(ids.split(","));
        return this.baseMapper.deleteBatchIds(permIds) > 0;
    }

    @Override
    public Boolean updatePerm(PermParam param) {
        SysPermission dbSysPerm = this.baseMapper.selectById(param.getId());
        Assert.isTrue(ObjectUtil.isNull(dbSysPerm), "修改的数据不存在");

        BeanUtil.copyProperties(param, dbSysPerm, true);
        return this.baseMapper.updateById(dbSysPerm) > 0;
    }

    @Override
    public SysPermission queryPerm(String id) {
        SysPermission dbSysPerm = this.baseMapper.selectById(id);
        Assert.isTrue(ObjectUtil.isNull(dbSysPerm), "查询的数据不存在");
        return dbSysPerm;
    }

    @Override
    public List<SysPermission> listPerm(PermParam param) {
        QueryWrapper<SysPermission> sysPermissionWrapper = new QueryWrapper();
        sysPermissionWrapper.lambda().
                eq(StrUtil.isNotBlank(param.getPerName()), SysPermission::getPerName, param.getPerName()).
                eq(StrUtil.isNotBlank(param.getPerCode()), SysPermission::getPerCode, param.getPerCode());
        return this.baseMapper.selectList(sysPermissionWrapper);
    }

    @Override
    public Set<String> getPermByUserId(String userId) {
        return null;
    }
}




