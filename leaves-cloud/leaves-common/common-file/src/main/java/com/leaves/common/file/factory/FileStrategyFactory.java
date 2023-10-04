package com.leaves.common.file.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.leaves.common.file.enums.FileStorageEnum;
import com.leaves.common.file.strategy.FileStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 策略模式工厂
 */

@Slf4j
public class FileStrategyFactory implements ApplicationContextAware {

    protected List<FileStrategy> strategyList = new ArrayList<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, FileStrategy> beansOfType = applicationContext.getBeansOfType(FileStrategy.class);
        beansOfType.forEach((k, v) -> {
            strategyList.add(v);
            log.info("初始化文件上传策略：" + k);
        });
    }

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
