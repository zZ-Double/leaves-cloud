package com.leaves.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaves.system.model.entity.SysUserRole;
import com.leaves.system.service.SysUserRoleService;
import com.leaves.system.mapper.SysUserRoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
* @author leaves
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2023-07-11 11:40:10
*/
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole>
    implements SysUserRoleService{

    @Override
    public Boolean setUserRoles(String roleIds, String userId) {
        boolean flag = false;
        // 更新用户时，如果角色id字符串为空，说明是将用户角色全部去除
        if (StrUtil.isBlank(roleIds)) {
            return removeUserRoles(Arrays.asList(userId));
        }
        List<String> roleIdList = Arrays.asList(roleIds.split(","));
        if (!CollectionUtils.isEmpty(roleIdList)) {
            removeUserRoles(Arrays.asList(userId));
            List<SysUserRole> userRoles = new ArrayList<>(roleIdList.size());
            roleIdList.forEach(role -> {
                SysUserRole sysUserRoles = new SysUserRole();
                sysUserRoles.setUserId(userId);
                sysUserRoles.setRoleId(role);
                userRoles.add(sysUserRoles);
            });
            flag = saveBatch(userRoles);
        }
        return flag;
    }

    @Override
    public Boolean removeUserRoles(List<String> userIds) {
        return remove(new QueryWrapper<SysUserRole>()
                .lambda()
                .in(ObjectUtil.isNotEmpty(userIds), SysUserRole::getUserId, userIds));
    }
}




