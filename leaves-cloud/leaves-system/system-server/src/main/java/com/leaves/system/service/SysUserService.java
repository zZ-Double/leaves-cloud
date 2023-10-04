package com.leaves.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leaves.common.enums.GenderEnum;
import com.leaves.system.model.entity.SysUser;
import com.leaves.system.model.form.PasswdForm;
import com.leaves.system.model.form.UserForm;
import com.leaves.system.model.param.UserParam;
import com.leaves.system.model.vo.UserVO;

import java.util.List;

/**
* @author leaves
* @description 针对表【sys_user(用户信息表)】的数据库操作Service
* @createDate 2023-07-11 11:40:10
*/
public interface SysUserService extends IService<SysUser> {

    Boolean saveUser(UserForm userForm);

    Boolean removeUser(String ids);

    Boolean updateUser(UserForm userForm);

    UserVO getUser(String id);

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

    /**
     * 重置用户密码
     * @param userId
     * @return
     */
    Boolean resetPasswd(String userId);

    /**
     * 修改个人密码
     * @return
     */
    Boolean modifyPasswd(PasswdForm form);


    /**
     * 获取当前用户信息
     * @return
     */
    UserVO userProfile();

    Boolean userAvatar(String avatarUrl);

    Boolean userInfo(UserForm form);

}
