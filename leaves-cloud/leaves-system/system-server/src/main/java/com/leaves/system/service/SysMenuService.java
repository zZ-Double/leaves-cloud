package com.leaves.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leaves.common.base.Option;
import com.leaves.system.model.entity.SysMenu;
import com.leaves.system.model.param.MenuParam;
import com.leaves.system.model.vo.MenuVO;

import java.util.List;
import java.util.Set;

/**
 * @author leaves
 * @description 针对表【sys_menu(菜单管理)】的数据库操作Service
 * @createDate 2023-07-12 19:39:43
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 新增菜单
     *
     * @param menu
     * @return
     */
    Boolean saveMenu(SysMenu menu);

    /**
     * 获取菜单表格列表
     *
     * @return
     */
    List<MenuVO> listMenus(MenuParam param);

    /**
     * 用户ID获取角色权限集合
     *
     * @param userId
     * @return
     */
    Set<String> listRolePerms(String userId);

    /**
     * 菜单下拉数据
     */
    List<Option> listMenuOptions();

}
