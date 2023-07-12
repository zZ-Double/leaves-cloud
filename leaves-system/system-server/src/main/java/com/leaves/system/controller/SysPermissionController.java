package com.leaves.system.controller;


import com.leaves.system.model.entity.SysPermission;
import com.leaves.system.model.param.PermParam;
import com.leaves.system.service.SysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author leaves
 * @since 2022-03-16
 */
@Api(tags = "权限管理")
@RestController
@RequestMapping("/api/v1/perm")
@RequiredArgsConstructor
public class SysPermissionController {

    private final SysPermissionService permissionService;


    //    @PreAuthorize("hasAuthority('sys:per:save')")
    @PostMapping(value = "/save")
    @ApiOperation(value = "新增权限")
//    @Log(title = "新增权限")
    public Boolean savePerm(@RequestBody @Validated(PermParam.add.class) PermParam param) {
        return permissionService.savePerm(param);
    }


    //    @PreAuthorize("hasAuthority('sys:per:remove')")
    @GetMapping(value = "/remove/{id}")
    @ApiOperation(value = "删除权限")
    public Boolean removePerm(@PathVariable String id) {
        return permissionService.removePerm(id);
    }


    //    @PreAuthorize("hasAuthority('sys:per:update')")
    @PostMapping(value = "/update")
    @ApiOperation(value = "修改权限基础信息")
//    @Log(title = "修改权限基础信息")
    public Boolean updatePerm(@RequestBody @Validated(PermParam.edit.class) PermParam param) {
        return permissionService.updatePerm(param);
    }


    //    @PreAuthorize("hasAuthority('sys:per:query')")
    @GetMapping(value = "/query/{id}")
    @ApiOperation(value = "权限ID查询权限")
    public SysPermission queryPermissionList(@PathVariable String id) {
        return permissionService.queryPerm(id);
    }

    //    @PreAuthorize("hasAuthority('sys:per:list')")
    @PostMapping(value = "/list")
    @ApiOperation(value = "查询权限", notes = "列表查询/分页查询；参数pageNum pageSize不为空则分页查询，否则列表查询")
//    @Log(title = "查询权限：列表查询/分页查询")
    public Object listPerm(@RequestBody PermParam param) {
        return permissionService.listPerm(param);
    }


}
