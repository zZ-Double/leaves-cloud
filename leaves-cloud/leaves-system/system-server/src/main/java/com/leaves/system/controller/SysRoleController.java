package com.leaves.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leaves.system.model.entity.SysRole;
import com.leaves.system.model.param.RoleParam;
import com.leaves.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 角色信息表 前端控制器
 * </p>
 *
 * @author leaves
 * @since 2022-03-16
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService roleService;

    //    @PreAuthorize("hasAuthority('sys:role:save')")
    @PostMapping(value = "/save")
    @ApiOperation(value = "新增角色")
//    @Log(title = "新增角色")
    public Boolean saveRole(@RequestBody @Validated(RoleParam.add.class) RoleParam param) {
        return roleService.saveRole(param);
    }


    //    @PreAuthorize("hasAuthority('sys:role:remove')")
    @GetMapping(value = "/remove/{id}")
    @ApiOperation(value = "删除角色")
    public Boolean removeRole(@PathVariable String id) {
        return roleService.removeRole(id);
    }


    //    @PreAuthorize("hasAuthority('sys:role:update')")
    @PostMapping(value = "/update")
    @ApiOperation(value = "修改角色信息")
//    @Log(title = "修改角色信息")
    public Boolean updateRole(@RequestBody @Validated(RoleParam.edit.class) RoleParam param) {
        return roleService.saveRole(param);
    }


    //    @PreAuthorize("hasAuthority('sys:role:query')")
    @GetMapping(value = "/query/{id}")
    @ApiOperation(value = "角色ID查询角色")
    public SysRole getRole(@PathVariable String id) {
        return roleService.getById(id);
    }


    @ApiOperation(value = "角色分页列表")
    @GetMapping("/pages")
    public IPage<SysRole> listRolePages(RoleParam param) {
        return roleService.listRolePages(param);
    }

    //    @PreAuthorize("hasAuthority('sys:role:list')")
    @GetMapping(value = "/list")
    @ApiOperation(value = "查询角色列表")
//    @Log(title = "查询角色列表")
    public Object listRole(RoleParam param) {
        return roleService.listRole(param);
    }

}
