package com.leaves.dubbo.api;

import com.leaves.common.result.Result;
import com.leaves.dubbo.dto.TestDTO;
import com.leaves.dubbo.vo.TestVO;

import java.util.concurrent.CompletableFuture;

/**
 * @author leaves
 * @version 1.0
 * @description
 * @date 2023/7/10
 */

public interface ITestProvider {

    CompletableFuture<Result<TestVO>> test(TestDTO dto);
}
