package com.leaves.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaves.common.base.BaseParam;
import com.leaves.common.model.Option;
import com.leaves.common.constant.GlobalConstants;
import com.leaves.common.enums.StatusEnum;
import com.leaves.common.security.utils.SecurityUtils;
import com.leaves.common.web.exception.BizException;
import com.leaves.system.mapper.SysMenuMapper;
import com.leaves.system.model.entity.SysMenu;
import com.leaves.system.model.entity.SysRole;
import com.leaves.system.model.entity.SysRoleMenu;
import com.leaves.system.model.entity.SysUserRole;
import com.leaves.system.model.enums.MenuTypeEnum;
import com.leaves.system.model.vo.MenuVO;
import com.leaves.system.model.vo.RouteVO;
import com.leaves.system.service.SysMenuService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author leaves
 * @description 针对表【sys_menu(菜单管理)】的数据库操作Service实现
 * @createDate 2023-07-12 19:39:43
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {


    /**
     * 保存菜单
     */
    @Override
    public Boolean saveMenu(SysMenu menu) {
        String path = menu.getPath();

        MenuTypeEnum menuType = menu.getType();  // 菜单类型
        switch (menuType) {
            case CATALOG: // 目录
                Assert.isTrue(path.startsWith("/"), "目录路由路径格式错误，必须以/开始");
                menu.setComponent("Layout");
                break;
            case EXT_LINK: // 外链
                menu.setComponent(null);
                break;
        }

        boolean result = this.saveOrUpdate(menu);
        return result;
    }

    @Override
    public Boolean removeMenu(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的数据不存在");
        List<String> menuIds = Arrays.asList(ids.split(","));
        try {
            Optional.ofNullable(menuIds)
                    .orElse(new ArrayList<>())
                    .forEach(id -> {
                        this.remove(new LambdaQueryWrapper<SysMenu>()
                                .and(StrUtil.isNotBlank(id),
                                        wrapper -> wrapper
                                                .eq(SysMenu::getId, id)
                                                .or()
                                                .eq(SysMenu::getParentId, id)));
                    });
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("删除菜单失败");
        }
        return true;
    }

    @Override
    public List<MenuVO> listMenus(BaseParam param) {
        List<SysMenu> menus = this.list(new LambdaQueryWrapper<SysMenu>()
                .like(StrUtil.isNotBlank(param.getKeywords()), SysMenu::getName, param.getKeywords())
                .orderByAsc(SysMenu::getSort)
        );

        Set<String> cacheMenuIds = menus.stream().map(menu -> menu.getId()).collect(Collectors.toSet());

        List<MenuVO> list = menus.stream().map(menu -> {
            String parentId = menu.getParentId();
            // parentId不在当前菜单ID的列表，说明为顶级菜单ID，根据此ID作为递归的开始条件节点
            if (!cacheMenuIds.contains(parentId)) {
                cacheMenuIds.add(parentId);
                return recurMenus(parentId, menus);
            }
            return new LinkedList<MenuVO>();
        }).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
        return list;
    }

    @Override
    public Set<String> listRolePerms(String userId) {
        Set<String> perms = this.baseMapper.listRolePerms(userId);
        return perms;
    }

    @Override
    public List<Option> listMenuOptions() {
        List<SysMenu> menuList = this.list(new LambdaQueryWrapper<SysMenu>().orderByAsc(SysMenu::getSort));
        List<Option> menus = recurMenuOptions(GlobalConstants.ROOT_NODE_ID, menuList);
        return menus;
    }

    @Override
    public List<RouteVO> listRoutes() {
        String userId = "";
        if (!SecurityUtils.isRoot()) {
            userId = SecurityUtils.getUserId();
        }
        List<MenuVO> menuList = this.baseMapper.listRoutes(userId);
        List<RouteVO> routeList = recurRoutes(GlobalConstants.ROOT_NODE_ID, menuList);
        return routeList;
    }

    /**
     * 递归生成菜单下拉层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private static List<Option> recurMenuOptions(String parentId, List<SysMenu> menuList) {
        if (CollectionUtil.isEmpty(menuList)) {
            return Collections.EMPTY_LIST;
        }

        List<Option> menus = menuList.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> new Option(menu.getId(), menu.getName(), recurMenuOptions(menu.getId(), menuList)))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return menus;
    }

    /**
     * 递归生成菜单列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private List<MenuVO> recurMenus(String parentId, List<SysMenu> menuList) {
        if (CollectionUtil.isEmpty(menuList)) {
            return Collections.EMPTY_LIST;
        }

        List<MenuVO> menus = menuList.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(entity -> {
                    MenuVO menuVO = new MenuVO();
                    BeanUtil.copyProperties(entity, menuVO);
                    List<MenuVO> children = recurMenus(entity.getId(), menuList);
                    menuVO.setChildren(children);
                    return menuVO;
                }).collect(Collectors.toList());
        return menus;
    }


    /**
     * 递归生成菜单路由层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private List<RouteVO> recurRoutes(String parentId, List<MenuVO> menuList) {
        List<RouteVO> list = new ArrayList<>();
        Optional.ofNullable(menuList).ifPresent(menus -> menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    RouteVO routeVO = new RouteVO();

                    MenuTypeEnum menuTypeEnum = menu.getType();

                    if (MenuTypeEnum.MENU.equals(menuTypeEnum)) {
                        routeVO.setName(menu.getPath()); //  根据name路由跳转 this.$router.push({name:xxx})
                    }
                    routeVO.setPath(menu.getPath()); // 根据path路由跳转 this.$router.push({path:xxx})
                    routeVO.setRedirect(menu.getRedirect());
                    routeVO.setComponent(menu.getComponent());

                    RouteVO.Meta meta = new RouteVO.Meta();
                    meta.setTitle(menu.getName());
                    meta.setIcon(menu.getIcon());
                    meta.setRoles(menu.getRoles());
                    meta.setHidden(StatusEnum.DISABLE.equals(menu.getStatus()));
                    meta.setKeepAlive(true);

                    routeVO.setMeta(meta);
                    List<RouteVO> children = recurRoutes(menu.getId(), menuList);
                    // 含有子节点的目录设置为可见
                    boolean alwaysShow = CollectionUtil.isNotEmpty(children) && children.stream().anyMatch(item -> item.getMeta().getHidden().equals(false));
                    meta.setAlwaysShow(alwaysShow);
                    routeVO.setChildren(children);

                    list.add(routeVO);
                }));
        return list;
    }
}




