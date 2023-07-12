package com.leaves.system.mapper;

import com.leaves.system.model.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

/**
* @author leaves
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2023-07-11 11:40:10
* @Entity com.leaves.system.model.entity.SysRole
*/
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 获取最大范围的数据权限
     *
     * @param userId
     * @return
     */
    Integer getMaximumDataScope(String userId);

}




