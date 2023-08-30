package com.leaves.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leaves.common.annotation.OperaLog;
import com.leaves.system.model.form.UserForm;
import com.leaves.system.model.param.UserParam;
import com.leaves.system.model.vo.UserVO;
import com.leaves.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class SysUserController {

    private final SysUserService userService;


    @ApiOperation(value = "新增用户")
    @PostMapping(value = "/save")
    @PreAuthorize("hasPerm('sys:user:save')")
    @OperaLog(title = "新增用户")
    public Boolean saveUser(@RequestBody @Validated UserForm form) {
        return userService.saveUser(form);
    }

    @ApiOperation(value = "删除用户")
    @GetMapping(value = "/remove/{id}")
    @PreAuthorize("hasPerm('sys:user:remove')")
    @OperaLog(title = "删除用户")
    public Boolean removeUser(@PathVariable String id) {
        return userService.removeUser(id);
    }

    @ApiOperation(value = "修改用户")
    @PostMapping(value = "/update")
    @PreAuthorize("hasPerm('sys:user:update')")
    @OperaLog(title = "修改用户")
    public Boolean updateUser(@RequestBody @Validated UserForm form) {
        return userService.updateUser(form);
    }

    @ApiOperation(value = "用户ID查询用户")
    @GetMapping(value = "/query/{id}")
    @PreAuthorize("hasPerm('sys:user:query')")
    @OperaLog(title = "用户ID查询用户")
    public UserVO getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @ApiOperation(value = "用户列表分页")
    @GetMapping(value = "/page")
    @PreAuthorize("hasPerm('sys:user:page')")
    @OperaLog(title = "用户列表分页")
    public IPage<UserVO> userPage(UserParam param) {
        return userService.userPage(param);
    }

    @ApiOperation(value = "获取当前登陆用户信息")
    @GetMapping(value = "/me")
    @OperaLog(title = "获取当前登陆用户信息")
    public UserVO me() {
        return userService.getLoginUserInfo();
    }

    @ApiOperation(value = "重置用户密码")
    @GetMapping(value = "/passwd/reset/{userId}")
    @PreAuthorize("hasPerm('sys:user:passwd:reset')")
    @OperaLog(title = "重置用户密码")
    public Boolean reset(@PathVariable String userId) {
        return userService.resetPasswd(userId);
    }

    @ApiOperation(value = "修改个人密码")
    @PostMapping(value = "/passwd/modify")
    @OperaLog(title = "修改个人密码")
    public Boolean modifyPasswd(String oldPasswd, String newPasswd, String confirmPasswd) {
        return userService.modifyPasswd(oldPasswd, newPasswd, confirmPasswd);
    }


}
