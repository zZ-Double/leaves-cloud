package com.leaves.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leaves.system.model.entity.SysUser;
import com.leaves.system.model.param.UserParam;
import com.leaves.system.model.vo.UserVO;

import java.util.List;

/**
* @author leaves
* @description 针对表【sys_user(用户信息表)】的数据库操作Service
* @createDate 2023-07-11 11:40:10
*/
public interface SysUserService extends IService<SysUser> {

    Boolean saveUser(UserParam param);

    Boolean removeUser(String ids);

    Boolean updateUser(UserParam param);

    UserVO getUser(String id);

    List<UserVO> listUser(UserParam param);

    Integer listUserByDeptId(List<String> deptIds);

}
