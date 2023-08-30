package com.leaves.common.file.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.leaves.common.file.enums.FileStorageEnum;
import com.leaves.common.file.strategy.FileStrategy;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;

/**
 * 策略模式工厂
 */

@Slf4j
public class FileStrategyFactory {

    @Resource
    private List<FileStrategy> strategyList;

    public FileStrategy getStrategy(FileStorageEnum fileStorage) {
        log.info("文件存储类型：{}", fileStorage.getName());
        if (CollectionUtil.isNotEmpty(strategyList)) {
            for (FileStrategy strategy : strategyList) {
                if (strategy.supports(fileStorage)) {
                    return strategy;
                }
            }
        }
        throw new RuntimeException(StrUtil.format("找不到文件存储类型：{}", fileStorage.getName()));
    }

}
