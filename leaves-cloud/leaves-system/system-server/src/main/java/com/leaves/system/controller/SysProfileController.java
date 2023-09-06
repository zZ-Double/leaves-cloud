package com.leaves.system.controller;

import com.leaves.common.annotation.OperaLog;
import com.leaves.system.model.form.PasswdForm;
import com.leaves.system.model.form.UserForm;
import com.leaves.system.model.vo.UserVO;
import com.leaves.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api("个人信息")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/profile")
public class SysProfileController {

    private final SysUserService userService;

    @ApiOperation(value = "获取当前用户信息")
    @GetMapping
    @OperaLog(title = "获取当前用户信息")
    public UserVO userProfile() {
        return userService.userProfile();
    }

    @ApiOperation(value = "修改用户头像")
    @GetMapping("/avatar")
    @OperaLog(title = "修改用户头像")
    public Boolean userAvatar(@ApiParam("用户头像Url") @RequestParam String avatarUrl) {
        return userService.userAvatar(avatarUrl);
    }

    @ApiOperation(value = "修改用户信息")
    @PostMapping("/info")
    @OperaLog(title = "修改用户信息")
    public Boolean userInfo(@RequestBody UserForm form) {
        return userService.userInfo(form);
    }

    @ApiOperation(value = "修改个人密码")
    @PostMapping(value = "/passwd")
    @OperaLog(title = "修改个人密码")
    public Boolean modifyPasswd(@RequestBody PasswdForm form) {
        return userService.modifyPasswd(form);
    }


}
