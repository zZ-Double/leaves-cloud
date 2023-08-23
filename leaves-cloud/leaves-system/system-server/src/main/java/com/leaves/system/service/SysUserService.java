package com.leaves.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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

    IPage<UserVO> userPage(UserParam param);

    Integer listUserByDeptId(List<String> deptIds);

    /**
     * 获取用户认证信息
     * @param userName
     * @return
     */
    UserVO getUserAuthInfo(String userName);

    /**
     * 获取当前登陆用户信息
     * @return
     */
    UserVO getLoginUserInfo();

}
