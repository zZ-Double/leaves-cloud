package com.leaves.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaves.common.interfaces.OperaLogInterface;
import com.leaves.common.model.OperaLogModel;
import com.leaves.system.mapper.SysOperationLogMapper;
import com.leaves.system.model.entity.SysOperationLog;
import com.leaves.system.service.SysOperationLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author leaves
 * @description 针对表【sys_operation_log(操作日志记录)】的数据库操作Service实现
 * @createDate 2023-07-11 11:40:10
 */
@Service
public class SysOperationLogServiceImpl extends ServiceImpl<SysOperationLogMapper, SysOperationLog>
        implements SysOperationLogService, OperaLogInterface {

    @Override
    @Async
    public void recordOperaLog(OperaLogModel operaLogModel) {
        SysOperationLog sysOperaLog = new SysOperationLog();
        BeanUtil.copyProperties(operaLogModel, sysOperaLog);
        sysOperaLog.setOperationTime(new Date());
        save(sysOperaLog);
    }
}




