package com.leaves.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leaves.common.annotation.OperaLog;
import com.leaves.common.base.BaseParam;
import com.leaves.common.model.Option;
import com.leaves.system.model.entity.SysRole;
import com.leaves.system.model.param.RoleParam;
import com.leaves.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色管理")
@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService roleService;

    @ApiOperation(value = "新增角色")
    @PostMapping(value = "/save")
    @PreAuthorize("hasPerm('sys:role:save')")
    @OperaLog(title = "新增角色")
    public Boolean saveRole(@RequestBody @Validated RoleParam param) {
        return roleService.saveRole(param);
    }

    @ApiOperation(value = "删除角色")
    @GetMapping(value = "/remove/{ids}")
    @PreAuthorize("hasPerm('sys:role:remove')")
    @OperaLog(title = "删除角色")
    public Boolean removeRole(@PathVariable String ids) {
        return roleService.removeRole(ids);
    }

    @ApiOperation(value = "修改角色信息")
    @PostMapping(value = "/update")
    @PreAuthorize("hasPerm('sys:role:update')")
    @OperaLog(title = "修改角色信息")
    public Boolean updateRole(@RequestBody @Validated(RoleParam.edit.class) RoleParam param) {
        return roleService.saveRole(param);
    }

    @ApiOperation(value = "角色ID查询角色")
    @GetMapping(value = "/query/{id}")
    @PreAuthorize("hasPerm('sys:role:query')")
    @OperaLog(title = "角色ID查询角色")
    public SysRole getRole(@PathVariable String id) {
        return roleService.getById(id);
    }

    @ApiOperation(value = "角色分页列表")
    @GetMapping("/page")
    @PreAuthorize("hasPerm('sys:role:page')")
    @OperaLog(title = "角色分页列表")
    public IPage<SysRole> listRolePages(BaseParam param) {
        return roleService.listRolePages(param);
    }

    @ApiOperation(value = "查询角色的菜单列表")
    @GetMapping(value = "/menus/{roleId}")
    @PreAuthorize("hasPerm('sys:role:menus:query')")
    @OperaLog(title = "查询角色的菜单列表")
    public List<String> getRoleMenuIds(@PathVariable String roleId) {
        return roleService.getRoleMenuIds(roleId);
    }

    @ApiOperation(value = "分配角色的资源权限")
    @PostMapping("/menus/{roleId}")
    @PreAuthorize("hasPerm('sys:role:menus:save')")
    @OperaLog(title = "分配角色的资源权限")
    public Boolean updateRoleMenus(@PathVariable String roleId, @RequestBody List<String> menuIds) {
        return roleService.updateRoleMenus(roleId, menuIds);
    }

    @ApiOperation(value = "角色下拉列表")
    @GetMapping(value = "/options")
    @PreAuthorize("hasPerm('sys:role:options')")
    @OperaLog(title = "角色下拉列表")
    public List<Option> roleOptions() {
        return roleService.roleOptions();
    }

}
