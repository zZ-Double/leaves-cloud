package com.leaves.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leaves.common.annotation.OperaLog;
import com.leaves.common.base.BaseParam;
import com.leaves.system.model.form.UserForm;
import com.leaves.system.model.param.UserParam;
import com.leaves.system.model.vo.UserVO;
import com.leaves.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class SysUserController {

    private final SysUserService userService;


    @PostMapping(value = "/save")
    @ApiOperation(value = "新增用户")
    public Boolean saveUser(@RequestBody @Validated UserForm form) {
        return userService.saveUser(form);
    }


    @GetMapping(value = "/remove/{id}")
    @ApiOperation(value = "删除用户")
    public Boolean removeUser(@PathVariable String id) {
        return userService.removeUser(id);
    }


    @PostMapping(value = "/update")
    @ApiOperation(value = "修改用户基础信息")
    public Boolean updateUser(@RequestBody @Validated UserForm form) {
        return userService.updateUser(form);
    }


    @GetMapping(value = "/query/{id}")
    @ApiOperation(value = "用户ID查询用户")
    public UserVO getUser(@PathVariable String id) {
        return userService.getUser(id);
    }


    @GetMapping(value = "/page")
    @ApiOperation(value = "用户列表分页")
    @OperaLog(title = "用户列表分页")
    public IPage<UserVO> userPage(UserParam param) {
        return userService.userPage(param);
    }

    @GetMapping(value = "/me")
    @ApiOperation(value = "获取当前登陆用户信息")
    @OperaLog(title = "获取当前登陆用户信息")
    public UserVO me() {
        return userService.getLoginUserInfo();
    }

}
