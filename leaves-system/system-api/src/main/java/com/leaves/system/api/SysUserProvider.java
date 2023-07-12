package com.leaves.system.api;

import com.leaves.common.result.Result;
import com.leaves.system.model.vo.UserVO;

import java.util.concurrent.CompletableFuture;

/**
 * @author leaves
 * @version 1.0
 * @description
 * @date 2023/7/12
 */

public interface SysUserProvider {

    /**
     * 获取用户认证信息
     * @param userName
     * @return
     */
    CompletableFuture<Result<UserVO>> getUserAuthInfo(String userName);
}
