package com.leaves.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leaves.system.model.entity.SysPermission;
import com.leaves.system.model.param.PermParam;

import java.util.List;
import java.util.Set;

/**
 * @author leaves
 * @description 针对表【sys_permission(权限表)】的数据库操作Service
 * @createDate 2023-07-11 11:40:10
 */
public interface SysPermissionService extends IService<SysPermission> {

    /**
     * 新增权限信息
     *
     * @param param
     * @return
     */
    Boolean savePerm(PermParam param);

    /**
     * 移除权限信息
     *
     * @param ids
     * @return
     */
    Boolean removePerm(String ids);

    /**
     * 修改权限信息
     *
     * @param param
     * @return
     */
    Boolean updatePerm(PermParam param);

    /**
     * 权限详情
     *
     * @param id
     * @return
     */
    SysPermission queryPerm(String id);

    /**
     * 查询权限列表
     *
     * @param param
     * @return
     */
    List<SysPermission> listPerm(PermParam param);

    /**
     * 用户Id查询权限列表
     *
     * @param userId
     * @return
     */
    Set<String> getPermByUserId(String userId);
}
