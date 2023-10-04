package com.leaves.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaves.common.enums.StatusEnum;
import com.leaves.system.model.entity.SysUserRole;
import com.leaves.system.service.SysUserRoleService;
import com.leaves.system.mapper.SysUserRoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author leaves
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2023-07-11 11:40:10
*/
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole>
    implements SysUserRoleService{

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean setUserRoles(List<String> roleIds, String userId) {

        // 获取当前用户角色关联
        List<SysUserRole> list = this.list(new QueryWrapper<SysUserRole>().lambda()
                .eq(SysUserRole::getUserId, userId));
        if (CollectionUtil.isNotEmpty(list)) {
            List<String> dbRoleIds = list.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
            boolean allMatch = dbRoleIds.stream().allMatch(r -> roleIds.contains(r));
            if (allMatch) {
                return true;
            }
        }

        // 更新用户时，如果角色id字符串为空，说明是将用户角色全部去除
        Boolean flag = removeUserRoles(Arrays.asList(userId));
        if (CollectionUtil.isEmpty(roleIds)) {
            return flag;
        }

        List<SysUserRole> userRoles = new ArrayList<>(roleIds.size());
        roleIds.forEach(role -> {
            SysUserRole sysUserRoles = new SysUserRole();
            sysUserRoles.setUserId(userId);
            sysUserRoles.setRoleId(role);
            userRoles.add(sysUserRoles);
        });
        return saveBatch(userRoles);
    }

    @Override
    public Boolean removeUserRoles(List<String> userIds) {
        return remove(new QueryWrapper<SysUserRole>()
                .lambda()
                .in(ObjectUtil.isNotEmpty(userIds), SysUserRole::getUserId, userIds));
    }
}




