package com.leaves.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaves.common.constant.GlobalConstants;
import com.leaves.common.enums.GenderEnum;
import com.leaves.common.enums.StatusEnum;
import com.leaves.common.security.utils.SecurityUtils;
import com.leaves.system.mapper.SysUserMapper;
import com.leaves.system.model.entity.SysUser;
import com.leaves.system.model.entity.SysUserRole;
import com.leaves.system.model.form.PasswdForm;
import com.leaves.system.model.form.UserForm;
import com.leaves.system.model.param.UserParam;
import com.leaves.system.model.vo.UserVO;
import com.leaves.system.service.SysMenuService;
import com.leaves.system.service.SysRoleService;
import com.leaves.system.service.SysUserRoleService;
import com.leaves.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author leaves
 * @description 针对表【sys_user(用户信息表)】的数据库操作Service实现
 * @createDate 2023-07-11 11:40:10 123
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserRoleService userRoleService;
    private final SysMenuService menuService;
    private final SysRoleService roleService;
    private final RedisTemplate redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveUser(UserForm userForm) {

        //以登录账户查询是否存在记录 存在则抛出异常
        SysUser dbSysUser = this.baseMapper.selectOne(new QueryWrapper<SysUser>()
                .lambda().eq(SysUser::getUsername, userForm.getUsername()));
        Assert.isTrue(ObjectUtil.isNull(dbSysUser), "新增用户失败,登录账号已存在");

        SysUser sysUser = new SysUser();
        //利用huTool BeanUtil 进行Bean拷贝, 忽略null
        BeanUtil.copyProperties(userForm, sysUser, true);
        sysUser.setPassword(new BCryptPasswordEncoder().encode(GlobalConstants.DEFAULT_USER_PASSWORD));

        boolean flag = this.baseMapper.insert(sysUser) > 0;
        //用户角色关联关系
        if (flag && CollectionUtil.isNotEmpty(userForm.getRoleIds())) {
            flag = userRoleService.setUserRoles(userForm.getRoleIds(), sysUser.getId());
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
    public Boolean updateUser(UserForm userForm) {
        //查询用户是否存在 不存在则抛出自定义异常
        SysUser dbSysUser = this.baseMapper.selectById(userForm.getId());
        Assert.isTrue(Objects.nonNull(dbSysUser), "当前用户不存在，请重试");

        BeanUtil.copyProperties(userForm, dbSysUser, true);
        boolean flag = this.baseMapper.updateById(dbSysUser) > 0;
        //用户角色关联关系
        if (flag) {
            flag = userRoleService.setUserRoles(userForm.getRoleIds(), dbSysUser.getId());
        }
        return flag;
    }

    @Override
    public UserVO getUser(String id) {
        SysUser dbSysUser = this.baseMapper.selectById(id);
        Assert.isTrue(Objects.nonNull(dbSysUser), "当前用户不存在，请重试");
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(dbSysUser, userVO);
        List<SysUserRole> userRoles = userRoleService.list(
                new QueryWrapper<SysUserRole>().lambda()
                        .select(SysUserRole::getRoleId)
                        .eq(SysUserRole::getUserId, dbSysUser.getId()));
        if (CollectionUtil.isNotEmpty(userRoles)) {
            userVO.setRoleIds(userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList()));
        }
        return userVO;
    }

    @Override
    public IPage<UserVO> userPage(UserParam param) {
        if (!SecurityUtils.isRoot()) {
            param.setTenantId(SecurityUtils.getTenantId());
        }
        // 查询数据
        return this.baseMapper.userPage(new Page<>(param.getCurrent(), param.getSize()), param);
    }

    @Override
    public Integer listUserByDeptId(List<String> deptIds) {
        Assert.isTrue(CollectionUtil.isNotEmpty(deptIds), "查询的数据不存在");
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(CollectionUtil.isNotEmpty(deptIds), SysUser::getDeptId, deptIds);
        return this.baseMapper.selectCount(queryWrapper);
    }

    @Override
    public UserVO getUserAuthInfo(String username) {
        // 用户名查询用户信息
        SysUser dbUser = this.getOne(new QueryWrapper<SysUser>().lambda().
                eq(SysUser::getUsername, username));
        Assert.isTrue(Objects.nonNull(dbUser), "查询的数据不存在");
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(dbUser, userVO, true);

        // 获取角色信息
        Set<String> roles = roleService.getRoles(dbUser.getId());
        userVO.setRoles(roles);

        // 获取权限
        Set<String> perms = menuService.listRolePerms(dbUser.getId());
        userVO.setPerms(perms);

        // 获取角色数据权限
        Integer dataScope = roleService.getMaximumDataScope(dbUser.getId());
        // 取最小值的数据权限
        if (Objects.nonNull(dbUser.getDataScope())) {
            dataScope = dataScope > dbUser.getDataScope() ? dbUser.getDataScope() : dataScope;
        }

        userVO.setDataScope(dataScope);
        return userVO;

    }

    @Override
    public UserVO getLoginUserInfo() {
        SysUser dbUser = this.getOne(new QueryWrapper<SysUser>().lambda()
                .eq(SysUser::getStatus, StatusEnum.ENABLE.getValue())
                .eq(SysUser::getId, SecurityUtils.getUserId())
                .select(
                        SysUser::getId,
                        SysUser::getNickName,
                        SysUser::getAvatar
                )
        );
        UserVO vo = new UserVO();
        BeanUtil.copyProperties(dbUser, vo, true);
        // 用户角色集合
        Set<String> roles = SecurityUtils.getRoles();
        vo.setRoles(roles);

        // 用户权限集合
        Set<String> perms = (Set<String>) redisTemplate.opsForValue().get(GlobalConstants.AUTH_PREFIX + vo.getId());
        vo.setPerms(perms);

        return vo;
    }

    @Override
    public Boolean resetPasswd(String userId) {
        Assert.isTrue(StrUtil.isNotBlank(userId), "用户ID不能为空");
        SysUser user = getById(userId);
        Assert.isTrue(Objects.nonNull(user), "未查询到用户信息，请刷新后重试");
        user.setPassword(new BCryptPasswordEncoder().encode(GlobalConstants.DEFAULT_USER_PASSWORD));
        return updateById(user);
    }

    @Override
    public Boolean modifyPasswd(PasswdForm form) {
        String userId = SecurityUtils.getUserId();
        Assert.isTrue(StrUtil.isNotBlank(userId), "用户ID不能为空");

        SysUser user = getById(userId);
        Assert.isTrue(Objects.nonNull(user), "未查询到用户信息，请刷新后重试");

        Assert.isTrue(form.getNewPasswd().equals(form.getConfirmPasswd()), "两次密码不一致");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Assert.isTrue((encoder.matches(form.getOldPasswd(), user.getPassword())), "原始密码输入错误");

        user.setPassword(new BCryptPasswordEncoder().encode(form.getNewPasswd()));
        return updateById(user);
    }

    @Override
    public UserVO userProfile() {
        String userId = SecurityUtils.getUserId();
        Assert.isTrue(StrUtil.isNotBlank(userId), "未查询到用户信息，请刷新后重试");
        return this.baseMapper.userProfile(userId);
    }

    @Override
    public Boolean userAvatar(String userAvatarUrl) {
        String userId = SecurityUtils.getUserId();
        Assert.isTrue(StrUtil.isNotBlank(userId), "用户ID不能为空");
        SysUser user = getById(userId);
        Assert.isTrue(Objects.nonNull(user), "未查询到用户信息，请刷新后重试");
        user.setAvatar(userAvatarUrl);
        return updateById(user);
    }

    @Override
    public Boolean userInfo(UserForm form) {
        String userId = SecurityUtils.getUserId();
        Assert.isTrue(StrUtil.isNotBlank(userId), "用户ID不能为空");
        SysUser user = getById(userId);
        Assert.isTrue(Objects.nonNull(user), "未查询到用户信息，请刷新后重试");
        user.setNickName(form.getNickName());
        user.setPhoneNumber(form.getPhoneNumber());
        user.setEmail(form.getEmail());
        user.setSex(form.getSex());
        return updateById(user);
    }
}




