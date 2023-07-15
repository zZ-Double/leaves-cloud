package com.leaves.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaves.common.constant.GlobalConstants;
import com.leaves.system.mapper.SysUserMapper;
import com.leaves.system.model.entity.SysUser;
import com.leaves.system.model.param.UserParam;
import com.leaves.system.model.vo.UserVO;
import com.leaves.system.service.SysMenuService;
import com.leaves.system.service.SysRoleService;
import com.leaves.system.service.SysUserRoleService;
import com.leaves.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author leaves
 * @description 针对表【sys_user(用户信息表)】的数据库操作Service实现
 * @createDate 2023-07-11 11:40:10
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserRoleService userRoleService;
    private final SysMenuService menuService;
    private final SysRoleService roleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveUser(UserParam param) {

        //以登录账户查询是否存在记录 存在则抛出异常
        SysUser dbSysUser = this.baseMapper.selectOne(new QueryWrapper<SysUser>()
                .lambda().eq(SysUser::getUserName, param.getUserName()));
        Assert.isTrue(ObjectUtil.isNull(dbSysUser), "新增用户失败,登录账号已存在");

        SysUser sysUser = new SysUser();
        //利用huTool BeanUtil 进行Bean拷贝, 忽略null
        BeanUtil.copyProperties(param, sysUser, true);
        sysUser.setPassword(new BCryptPasswordEncoder().encode(GlobalConstants.DEFAULT_USER_PASSWORD));

        boolean flag = this.baseMapper.insert(sysUser) > 0;
        //用户角色关联关系
        if (flag && StrUtil.isNotBlank(param.getRoleIds())) {
            flag = userRoleService.setUserRoles(param.getRoleIds(), sysUser.getId());
        }
        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeUser(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的数据为空");
        List<String> userIds = Arrays.asList(ids.split(","));
        // 移除用户角色
        userRoleService.removeUserRoles(userIds);
        // 删除用户
        return this.baseMapper.deleteBatchIds(userIds) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateUser(UserParam param) {
        //查询用户是否存在 不存在则抛出自定义异常
        SysUser dbSysUser = this.baseMapper.selectById(param.getId());
        Assert.isTrue(Objects.nonNull(dbSysUser), "当前用户不存在，请重试");

        BeanUtil.copyProperties(param, dbSysUser, true);
        boolean flag = this.baseMapper.updateById(dbSysUser) > 0;
        //用户角色关联关系
        if (flag) {
            flag = userRoleService.setUserRoles(param.getRoleIds(), dbSysUser.getId());
        }
        return flag;
    }

    @Override
    public UserVO getUser(String id) {
        SysUser dbSysUser = this.baseMapper.selectById(id);
        Assert.isTrue(Objects.nonNull(dbSysUser), "当前用户不存在，请重试");
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(dbSysUser, userVO);
        return userVO;
    }

    @Override
    public List<UserVO> listUser(UserParam param) {
        QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper();
        sysUserQueryWrapper.lambda().
                eq(StrUtil.isNotBlank(param.getUserName()), SysUser::getUserName, param.getUserName()).
                eq(StrUtil.isNotBlank(param.getPhoneNumber()), SysUser::getPhoneNumber, param.getPhoneNumber());
        List<SysUser> sysUsers = this.baseMapper.selectList(sysUserQueryWrapper);
        List<UserVO> userVOS = BeanUtil.copyToList(sysUsers, UserVO.class);
        return userVOS;
    }

    @Override
    public Integer listUserByDeptId(List<String> deptIds) {
        Assert.isTrue(CollectionUtil.isNotEmpty(deptIds), "查询的数据不存在");
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(CollectionUtil.isNotEmpty(deptIds), SysUser::getDeptId, deptIds);
        return this.baseMapper.selectCount(queryWrapper);
    }

    @Override
    public UserVO getUserAuthInfo(String userName) {
        // 用户名查询用户信息
        SysUser dbUser = this.getOne(new QueryWrapper<SysUser>().lambda().
                eq(SysUser::getUserName, userName));
        Assert.isTrue(Objects.nonNull(dbUser), "查询的数据不存在");
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(dbUser, userVO, true);

        // 获取权限
        Set<String> perms = menuService.listRolePerms(dbUser.getId());
        userVO.setPerms(perms);
        // 获取数据权限
        Integer dataScope = roleService.getMaximumDataScope(dbUser.getId());
        userVO.setDataScope(dataScope);
        return userVO;

    }
}




