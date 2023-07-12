package com.leaves.system.controller;


import com.leaves.system.model.param.UserParam;
import com.leaves.system.model.vo.UserVO;
import com.leaves.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author leaves
 * @since 2022-03-16
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/v1/user")
public class SysUserController {

    @Autowired
    private SysUserService userService;


    @PostMapping(value = "/save")
    @ApiOperation(value = "新增用户")
    public Boolean saveUser(@RequestBody @Validated(UserParam.add.class) UserParam param) {
        return userService.saveUser(param);
    }


    @GetMapping(value = "/remove/{id}")
    @ApiOperation(value = "删除用户")
    public Boolean removeUser(@PathVariable String id) {
        return userService.removeUser(id);
    }


    @PostMapping(value = "/update")
    @ApiOperation(value = "修改用户基础信息")
    public Boolean updateUser(@RequestBody @Validated(UserParam.edit.class) UserParam param) {
        return userService.updateUser(param);
    }


    @GetMapping(value = "/query/{id}")
    @ApiOperation(value = "用户ID查询用户")
    public UserVO getUser(@PathVariable String id) {
        return userService.getUser(id);
    }


    @GetMapping(value = "/list")
    @ApiOperation(value = "查询用户列表")
    public List<UserVO> queryUserList(UserParam param) {
        return userService.listUser(param);
    }

}
