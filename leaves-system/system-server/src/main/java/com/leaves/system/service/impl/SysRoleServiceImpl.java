package com.leaves.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaves.system.mapper.SysRoleMapper;
import com.leaves.system.model.entity.SysRole;
import com.leaves.system.model.entity.SysRoleMenu;
import com.leaves.system.model.entity.SysUserRole;
import com.leaves.system.model.param.RoleParam;
import com.leaves.system.service.SysRoleMenuService;
import com.leaves.system.service.SysRoleService;
import com.leaves.system.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author leaves
 * @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
 * @createDate 2023-07-11 11:40:10
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMenuService roleMenuService;
    private final SysUserRoleService userRoleService;

    @Override
    public Boolean saveRole(RoleParam param) {

        long count = this.count(new LambdaQueryWrapper<SysRole>()
                .ne(StrUtil.isNotBlank(param.getId()), SysRole::getId, param.getId())
                .and(wrapper ->
                        wrapper.eq(SysRole::getRoleCode, param.getRoleCode())
                                .or().eq(SysRole::getRoleName, param.getRoleName())
                ));
        Assert.isTrue(count <= 0, "角色名称或角色编码重复，请检查！");

        SysRole sysRole = new SysRole();
        BeanUtil.copyProperties(param, sysRole, true);
        return this.saveOrUpdate(sysRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeRole(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的数据不存在");
        List<String> roleIds = Arrays.asList(ids.split(","));

        Optional.ofNullable(roleIds)
                .orElse(new ArrayList<>())
                .forEach(id -> {
                    long count = userRoleService.count(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId, id));
                    Assert.isTrue(count <= 0, "该角色已分配用户，无法删除");
                    roleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, id));
                });

        return this.removeByIds(roleIds);
    }

    @Override
    public IPage<SysRole> listRolePages(RoleParam param) {
        // 查询数据
        return this.page(new Page<>(param.getCurrent(), param.getSize()),
                new LambdaQueryWrapper<SysRole>()
                        .eq(StrUtil.isNotBlank(param.getStatus()), SysRole::getStatus, param.getStatus())
                        .like(StrUtil.isNotBlank(param.getRoleName()), SysRole::getRoleName, param.getRoleName())
                        .like(StrUtil.isNotBlank(param.getRoleCode()), SysRole::getRoleCode, param.getRoleCode()));
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

    @Override
    public Integer getMaximumDataScope(String userId) {
        return this.baseMapper.getMaximumDataScope(userId);
    }

    @Override
    public List<String> getRoleMenuIds(String roleId) {
        return roleMenuService.listMenuIdsByRoleId(roleId);
    }

    @Override
    public Boolean updateRoleMenus(String roleId, List<String> menuIds) {
        // 删除角色菜单
        boolean flag = roleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));
        // 新增角色菜单
        if (CollectionUtil.isNotEmpty(menuIds)) {
            List<SysRoleMenu> roleMenus = menuIds.stream()
                    .map(menuId -> new SysRoleMenu(roleId, menuId))
                    .collect(Collectors.toList());
            flag = roleMenuService.saveBatch(roleMenus);
        }
        return flag;
    }
}




