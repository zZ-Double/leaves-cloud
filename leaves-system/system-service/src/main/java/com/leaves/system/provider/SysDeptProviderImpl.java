package com.leaves.system.provider;

import com.leaves.common.base.BaseProvider;
import com.leaves.common.result.Result;
import com.leaves.system.api.SysDeptProvider;
import com.leaves.system.model.param.DeptParam;
import com.leaves.system.service.SysDeptService;
import com.leaves.system.model.vo.DeptVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author leaves
 * @version 1.0
 * @description
 * @date 2023/7/11
 */
@DubboService(async = true)
@Slf4j
public class SysDeptProviderImpl extends BaseProvider implements SysDeptProvider {

    @Autowired
    private SysDeptService deptService;

    @Override
    public CompletableFuture<Result<Boolean>> saveDept(DeptParam sysDeptReq) {
        return null;
    }

    @Override
    public CompletableFuture<Result<Boolean>> removeDept(String ids) {
        return null;
    }

    @Override
    public CompletableFuture<Result<Boolean>> updateDept(DeptParam sysDept) {
        return null;
    }

    @Override
    public CompletableFuture<Result<DeptVO>> getDept(String id) {
        return null;
    }

    @Override
    public CompletableFuture<Result<List<DeptVO>>> listDept(DeptParam sysDeptReq) {
        return null;
    }
}
