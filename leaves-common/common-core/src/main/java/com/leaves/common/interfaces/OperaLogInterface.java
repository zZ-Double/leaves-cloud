package com.leaves.common.interfaces;

import com.leaves.common.model.OperaLogModel;
import org.springframework.scheduling.annotation.Async;

/**
 * 异步处理操作日志
 */
public interface OperaLogInterface {

    @Async
    void recordOperaLog(OperaLogModel operaLogModel);
}
