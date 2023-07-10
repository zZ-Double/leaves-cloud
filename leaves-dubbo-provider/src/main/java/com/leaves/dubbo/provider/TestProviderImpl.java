package com.leaves.dubbo.provider;

import com.leaves.common.result.Result;
import com.leaves.dubbo.api.ITestProvider;
import com.leaves.dubbo.dto.TestDTO;
import com.leaves.dubbo.vo.TestVO;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.concurrent.CompletableFuture;

/**
 * @author leaves
 * @version 1.0
 * @description
 * @date 2023/7/10
 */
@DubboService(async = true)
public class TestProviderImpl implements ITestProvider {

    @Override
    public CompletableFuture<Result<TestVO>> test(TestDTO dto) {
        return CompletableFuture.supplyAsync(() -> {
            TestVO vo = new TestVO();
            vo.setMsg(dto.getName() + " " + dto.getAge());
            return Result.success(vo);
        });
    }
}
