package com.leaves.system.service;

import com.leaves.system.model.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leaves.system.model.param.RoleParam;

import java.util.List;

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
     * 修改角色
     * @param param
     * @return
     */
    Boolean updateRole(RoleParam param);

    /**
     * 角色详情
     * @param id
     * @return
     */
    SysRole getRole(String id);

    /**
     * 角色列表
     * @param param
     * @return
     */
    List<SysRole> listRole(RoleParam param);
}
