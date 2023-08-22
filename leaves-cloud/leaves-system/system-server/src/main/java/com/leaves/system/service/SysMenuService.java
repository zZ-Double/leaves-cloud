package com.leaves.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leaves.common.base.BaseParam;
import com.leaves.common.model.Option;
import com.leaves.system.model.entity.SysMenu;
import com.leaves.system.model.vo.MenuVO;
import com.leaves.system.model.vo.RouteVO;

import java.util.List;
import java.util.Set;

/**
 * @author leaves
 * @description 针对表【sys_menu(菜单管理)】的数据库操作Service
 * @createDate 2023-07-12 19:39:43
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 新增修改菜单
     */
    Boolean saveMenu(SysMenu menu);

    /**
     * 获取菜单表格列表
     */
    List<MenuVO> listMenus(BaseParam param);

    /**
     * 用户ID获取角色权限集合
     */
    Set<String> listRolePerms(String userId);

    /**
     * 菜单下拉数据
     */
    List<Option> listMenuOptions();

    /**
     * 路由列表
     */
    List<RouteVO> listRoutes();

}
