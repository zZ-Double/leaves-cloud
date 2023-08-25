package com.leaves.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leaves.common.base.BaseParam;
import com.leaves.common.model.Option;
import com.leaves.system.model.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leaves.system.model.param.RoleParam;

import java.util.List;
import java.util.Set;

/**
* @author leaves
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2023-07-11 11:40:10
*/
public interface SysRoleService extends IService<SysRole> {

    /**
     * 新增角色
     * @param param
     * @return
     */
    Boolean saveRole(RoleParam param);

    /**
     * 移除角色
     * @param ids
     * @return
     */
    Boolean removeRole(String ids);

    /**
     * 角色分页列表
     *
     * @param param
     * @return
     */
    IPage<SysRole> listRolePages(BaseParam param);

    /**
     * 获取最大范围的数据权限
     *
     * @param userId
     * @return
     */
    Integer getMaximumDataScope(String userId);

    /**
     * 获取角色的资源ID集合,资源包括菜单和权限
     *
     * @param roleId
     * @return
     */
    List<String> getRoleMenuIds(String roleId);

    /**
     * 修改角色的资源权限
     *
     * @param roleId
     * @param menuIds
     * @return
     */
    Boolean updateRoleMenus(String roleId, List<String> menuIds);

    /**
     * 用户登陆时将用户角色设置到UserDetails中
     * @param userId
     * @return
     */
    Set<String> getRoles(String userId);


    List<Option> roleOptions();
}
