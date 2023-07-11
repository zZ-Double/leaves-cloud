package com.leaves.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaves.system.model.entity.SysUser;
import com.leaves.system.service.SysUserService;
import com.leaves.system.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

/**
* @author leaves
* @description 针对表【sys_user(用户信息表)】的数据库操作Service实现
* @createDate 2023-07-11 11:40:10
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

}




