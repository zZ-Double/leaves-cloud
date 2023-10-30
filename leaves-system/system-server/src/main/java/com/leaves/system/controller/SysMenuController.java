package com.leaves.system.controller;

import com.leaves.common.annotation.OperaLog;
import com.leaves.common.base.BaseParam;
import com.leaves.common.model.Option;
import com.leaves.system.model.entity.SysMenu;
import com.leaves.system.model.vo.MenuVO;
import com.leaves.system.model.vo.RouteVO;
import com.leaves.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Api(tags = "菜单管理")
@RestController
@RequestMapping("/api/v1/menu")
@RequiredArgsConstructor
@Slf4j
public class SysMenuController {

    private final SysMenuService menuService;

    @ApiOperation(value = "新增菜单")
    @PostMapping(value = "/save")
    @PreAuthorize("hasPerm('sys:menu:save')")
    @OperaLog(title = "新增菜单")
    public Boolean saveMenu(@RequestBody SysMenu menu) {
        return menuService.saveMenu(menu);
    }

    @ApiOperation(value = "删除菜单")
    @GetMapping("remove/{ids}")
    @PreAuthorize("hasPerm('sys:menu:remove')")
    @OperaLog(title = "删除菜单")
    public Boolean removeMenus(@ApiParam("菜单ID，多个以英文(,)分割") @PathVariable("ids") String ids) {
        return menuService.removeMenu(ids);
    }

    @ApiOperation(value = "修改菜单")
    @PostMapping(value = "/update")
    @PreAuthorize("hasPerm('sys:menu:update')")
    @OperaLog(title = "修改菜单")
    public Boolean updateMenu(@RequestBody SysMenu menu) {
        return menuService.saveMenu(menu);
    }

    @ApiOperation(value = "菜单详情")
    @GetMapping("/query/{id}")
    @PreAuthorize("hasPerm('sys:menu:query')")
    @OperaLog(title = "菜单详情")
    public SysMenu getMenu(@ApiParam(value = "菜单ID") @PathVariable String id) {
        return menuService.getById(id);
    }

    @ApiOperation(value = "菜单列表")
    @GetMapping("/list")
    @PreAuthorize("hasPerm('sys:menu:list')")
    @OperaLog(title = "菜单列表")
    public List<MenuVO> listMenus(BaseParam param) {
        return menuService.listMenus(param);
    }

    @ApiOperation(value = "菜单下拉列表")
    @GetMapping("/options")
    @PreAuthorize("hasPerm('sys:menu:options')")
    @OperaLog(title = "菜单下拉列表")
    public List<Option> options() {
        return menuService.listMenuOptions();
    }

    @ApiOperation(value = "路由列表")
    @GetMapping("/routes")
    @OperaLog(title = "路由列表")
    public List<RouteVO> routes() {
        return menuService.listRoutes();
    }

}

