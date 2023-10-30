package com.leaves.system.provider;

import com.leaves.common.base.BaseProvider;
import com.leaves.common.result.Result;
import com.leaves.system.api.SysUserProvider;
import com.leaves.system.model.vo.UserVO;
import com.leaves.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.concurrent.CompletableFuture;

/**
 * @author leaves
 * @version 1.0
 * @description
 * @date 2023/7/12
 */
@Slf4j
@DubboService(async = true)
@RequiredArgsConstructor
public class SysUserProviderImpl extends BaseProvider implements SysUserProvider {

    private final SysUserService userService;

    @Override
    public CompletableFuture<Result<UserVO>> getUserAuthInfo(String userName) {
        return this.buildFuture(Result.success(userService.getUserAuthInfo(userName)));
    }
}
