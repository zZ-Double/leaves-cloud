package com.leaves.system.controller;

import com.leaves.common.base.Option;
import com.leaves.system.model.entity.SysMenu;
import com.leaves.system.model.param.MenuParam;
import com.leaves.system.model.vo.MenuVO;
import com.leaves.system.model.vo.RouteVO;
import com.leaves.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Api(tags = "菜单接口")
@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
@Slf4j
public class SysMenuController {

    private final SysMenuService menuService;

    @ApiOperation(value = "新增菜单")
    @PostMapping(value = "/save")
    public Boolean saveMenu(@RequestBody SysMenu menu) {
        return menuService.saveMenu(menu);
    }

    @ApiOperation(value = "删除菜单")
    @GetMapping("remove/{ids}")
    public Boolean removeMenus(@ApiParam("菜单ID，多个以英文(,)分割") @PathVariable("ids") String ids) {
        return menuService.removeByIds(Arrays.asList(ids.split(",")));
    }

    @ApiOperation(value = "修改菜单")
    @PostMapping(value = "/update")
    public Boolean updateMenu(@RequestBody SysMenu menu) {
        return menuService.saveMenu(menu);
    }

    @ApiOperation(value = "菜单详情")
    @GetMapping("/query/{id}")
    public SysMenu getMenu(@ApiParam(value = "菜单ID") @PathVariable String id) {
        return menuService.getById(id);
    }

    @ApiOperation(value = "菜单列表")
    @GetMapping("/list")
    public List<MenuVO> listMenus(MenuParam param) {
        return menuService.listMenus(param);
    }

    @ApiOperation(value = "菜单下拉列表")
    @GetMapping("/options")
    public List<Option> options() {
        return menuService.listMenuOptions();
    }

    @ApiOperation(value = "路由列表")
    @GetMapping("/routes")
    public List<RouteVO> routes() {
        return menuService.listRoutes();
    }

}

