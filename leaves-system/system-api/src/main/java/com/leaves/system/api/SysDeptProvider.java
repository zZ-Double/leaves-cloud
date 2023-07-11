package com.leaves.system.api;

import com.leaves.common.result.Result;
import com.leaves.system.model.vo.DeptVO;
import com.leaves.system.model.param.DeptParam;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author leaves
 * @version 1.0
 * @description
 * @date 2023/7/11
 */

public interface SysDeptProvider {

    /**
     * 新增
     *
     * @param param
     * @return
     */
    CompletableFuture<Result<Boolean>> saveDept(DeptParam param);

    /**
     * 删除
     *
     * @param ids 多个用,隔开
     * @return
     */
    CompletableFuture<Result<Boolean>> removeDept(String ids);

    /**
     * 修改
     *
     * @param param
     * @return
     */
    CompletableFuture<Result<Boolean>> updateDept(DeptParam param);

    /**
     * 详情
     *
     * @param id
     * @return
     */
    CompletableFuture<Result<DeptVO>> getDept(String id);

    /**
     * 列表
     *
     * @param param
     * @return
     */
    CompletableFuture<Result<List<DeptVO>>> listDept(DeptParam param);
}
