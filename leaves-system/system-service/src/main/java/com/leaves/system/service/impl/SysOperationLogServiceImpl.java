package com.leaves.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaves.system.model.entity.SysOperationLog;
import com.leaves.system.service.SysOperationLogService;
import com.leaves.system.mapper.SysOperationLogMapper;
import org.springframework.stereotype.Service;

/**
* @author leaves
* @description 针对表【sys_operation_log(操作日志记录)】的数据库操作Service实现
* @createDate 2023-07-11 11:40:10
*/
@Service
public class SysOperationLogServiceImpl extends ServiceImpl<SysOperationLogMapper, SysOperationLog>
    implements SysOperationLogService{

}




