package com.leaves.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leaves.common.base.BaseParam;
import com.leaves.system.model.entity.SysRole;
import com.leaves.system.model.param.RoleParam;
import com.leaves.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
    public Boolean saveRole(@RequestBody @Validated RoleParam param) {
        return roleService.saveRole(param);
    }

    @ApiOperation(value = "删除角色")
    @GetMapping(value = "/remove/{ids}")
    public Boolean removeRole(@PathVariable String ids) {
        return roleService.removeRole(ids);
    }


    @ApiOperation(value = "修改角色信息")
    @PostMapping(value = "/update")
    public Boolean updateRole(@RequestBody @Validated(RoleParam.edit.class) RoleParam param) {
        return roleService.saveRole(param);
    }

    @ApiOperation(value = "角色ID查询角色")
    @GetMapping(value = "/query/{id}")
    public SysRole getRole(@PathVariable String id) {
        return roleService.getById(id);
    }


    @ApiOperation(value = "角色分页列表")
    @GetMapping("/pages")
    public IPage<SysRole> listRolePages(BaseParam param) {
        return roleService.listRolePages(param);
    }

    @ApiOperation(value = "查询角色列表")
    @GetMapping(value = "/list")
    public List<SysRole> listRole(BaseParam param) {
        return roleService.listRole(param);
    }

    @ApiOperation(value = "查询角色的菜单列表")
    @GetMapping(value = "/menus/{roleId}")
    public List<String> getRoleMenuIds(@PathVariable String roleId) {
        return roleService.getRoleMenuIds(roleId);
    }

    @ApiOperation(value = "分配角色的资源权限")
    @PostMapping("/menus/{roleId}")
    public Boolean updateRoleMenus(@PathVariable String roleId, @RequestBody List<String> menuIds) {
        return roleService.updateRoleMenus(roleId, menuIds);
    }

}
