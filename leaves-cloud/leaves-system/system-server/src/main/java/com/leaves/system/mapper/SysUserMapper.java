package com.leaves.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leaves.system.model.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leaves.system.model.param.UserParam;
import com.leaves.system.model.vo.UserVO;
import org.apache.ibatis.annotations.Param;

/**
* @author leaves
* @description 针对表【sys_user(用户信息表)】的数据库操作Mapper
* @createDate 2023-07-11 11:40:10
* @Entity com.leaves.system.model.entity.SysUser
*/
public interface SysUserMapper extends BaseMapper<SysUser> {

    IPage<UserVO> userPage(Page<SysUser> page, @Param("param") UserParam param);

}




