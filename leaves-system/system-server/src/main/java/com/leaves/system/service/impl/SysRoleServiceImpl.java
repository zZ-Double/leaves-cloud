package com.leaves.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaves.system.mapper.SysRoleMapper;
import com.leaves.system.model.entity.SysRole;
import com.leaves.system.model.param.RoleParam;
import com.leaves.system.service.SysRolePermissionService;
import com.leaves.system.service.SysRoleService;
import com.sun.xml.internal.bind.v2.TODO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author leaves
 * @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
 * @createDate 2023-07-11 11:40:10
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRolePermissionService rolePermissionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveRole(RoleParam param) {
        // 以角色名称或者角色编码查询是否存在记录
        SysRole dbSysRole = this.baseMapper.selectOne(new QueryWrapper<SysRole>()
                .lambda()
                .eq(SysRole::getRoleName, param.getRoleName())
                .or()
                .eq(SysRole::getRoleCode, param.getRoleCode()));
        // 存在则抛出异常
        Assert.isTrue(ObjectUtil.isNotNull(dbSysRole), "角色名称" + param.getRoleName()
                + "或角色CODE" + param.getRoleCode() + "已存在");

        SysRole sysRole = new SysRole();
        BeanUtil.copyProperties(param, sysRole, true);

        boolean flag = this.baseMapper.insert(sysRole) > 0;
        //角色权限关联关系
        if (flag && StrUtil.isNotBlank(param.getPermissionIds())) {
            flag = rolePermissionService.setRolePermissions(param.getPermissionIds(), sysRole.getId());
        }
        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeRole(String ids) {
        Assert.isTrue(StrUtil.isBlank(ids), "删除的数据不存在");
        List<String> roleIds = Arrays.asList(ids.split(","));
        // 移除角色权限
        rolePermissionService.removeRolePermissions(roleIds);
        // 移除角色
        return this.baseMapper.deleteBatchIds(roleIds) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateRole(RoleParam param) {
        // 查询是否存在
        SysRole dbSysRole = this.baseMapper.selectById(param.getId());
        // 不存在则抛出自定义异常
        Assert.isTrue(ObjectUtil.isNull(dbSysRole), "修改的数据不存在");

        BeanUtil.copyProperties(param, dbSysRole, true);
        boolean flag = this.baseMapper.updateById(dbSysRole) > 0;
        // 用户角色关联关系
        if (flag) {
            flag = rolePermissionService.setRolePermissions(param.getPermissionIds(), dbSysRole.getId());
        }
        return flag;
    }

    @Override
    public SysRole getRole(String id) {
        // TODO 是否关联用户权限 后续再考虑
        SysRole dbSysRole = this.baseMapper.selectById(id);
        Assert.isTrue(ObjectUtil.isNull(dbSysRole), "查询的数据不存在");
        return dbSysRole;
    }

    @Override
    public List<SysRole> listRole(RoleParam param) {
        QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper();
        sysRoleQueryWrapper.lambda().
                eq(StrUtil.isNotBlank(param.getStatus()), SysRole::getStatus, param.getStatus()).
                like(StrUtil.isNotBlank(param.getRoleName()), SysRole::getRoleName, param.getRoleName()).
                like(StrUtil.isNotBlank(param.getRoleCode()), SysRole::getRoleCode, param.getRoleCode());
        return this.baseMapper.selectList(sysRoleQueryWrapper);
    }
}




