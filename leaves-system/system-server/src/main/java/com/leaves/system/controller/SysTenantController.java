package com.leaves.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leaves.common.annotation.OperaLog;
import com.leaves.common.base.BaseParam;
import com.leaves.common.model.Option;
import com.leaves.common.security.utils.SecurityUtils;
import com.leaves.system.model.entity.SysTenant;
import com.leaves.system.model.param.TenantParam;
import com.leaves.system.service.SysTenantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Api("租户管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tenant")
public class SysTenantController {

    private final SysTenantService tenantService;

    @ApiOperation(value = "新增租户")
    @PostMapping(value = "/save")
    @PreAuthorize("hasPerm('sys:tenant:save')")
    @OperaLog(title = "新增租户")
    public Boolean saveTenant(@RequestBody SysTenant sysTenant) {
        return tenantService.tenantSave(sysTenant);
    }

    @ApiOperation(value = "删除租户")
    @GetMapping(value = "/remove/{ids}")
    @PreAuthorize("hasPerm('sys:tenant:remove')")
    @OperaLog(title = "删除租户")
    public Boolean removeTenant(@PathVariable String ids) {
        return tenantService.removeByIds(Arrays.asList(ids.split(",")));
    }

    @ApiOperation(value = "修改租户")
    @PostMapping(value = "/update")
    @PreAuthorize("hasPerm('sys:tenant:update')")
    @OperaLog(title = "修改租户")
    public Boolean updateTenant(@RequestBody SysTenant sysTenant) {
        return tenantService.tenantUpdate(sysTenant);
    }

    @ApiOperation(value = "租户分页")
    @GetMapping(value = "/page")
    @PreAuthorize("hasPerm('sys:tenant:page')")
    @OperaLog(title = "租户分页")
    public IPage<SysTenant> pageTenant(TenantParam param) {
        return tenantService.tenantPage(param);
    }

    @ApiOperation(value = "租户详情")
    @GetMapping(value = "/query/{id}")
    @PreAuthorize("hasPerm('sys:tenant:query')")
    @OperaLog(title = "租户详情")
    public SysTenant tenant(@PathVariable String id) {
        return tenantService.tenantQuery(id);
    }

    @ApiOperation(value = "查询租户的菜单列表")
    @GetMapping(value = "/menus/{tenantId}")
    @PreAuthorize("hasPerm('sys:tenant:menus:query')")
    @OperaLog(title = "查询租户的菜单列表")
    public List<String> getTenantMenuIds(@PathVariable String tenantId) {
        return tenantService.getTenantMenuIds(tenantId);
    }

    @ApiOperation(value = "分配租户的资源权限")
    @PostMapping("/menus/{tenantId}")
    @PreAuthorize("hasPerm('sys:tenant:menus:save')")
    @OperaLog(title = "分配租户的资源权限")
    public Boolean updateRoleMenus(@PathVariable String tenantId, @RequestBody List<String> menuIds) {
        return tenantService.updateTenantMenus(tenantId, menuIds);
    }

    @ApiOperation(value = "租户下拉列表")
    @GetMapping(value = "/options")
    @PreAuthorize("hasPerm('sys:tenant:options')")
    @OperaLog(title = "租户下拉列表")
    public List<Option> tenantOptions(@RequestParam(required = false) String tenantId) {
        if (!SecurityUtils.isRoot()) {
            tenantId = SecurityUtils.getTenantId();
        }
        return tenantService.tenantOptions(tenantId);
    }
}
