package com.leaves.common.file.enums;

import com.leaves.common.base.IBaseEnum;
import lombok.Getter;

/**
 * @author leaves
 * @version 1.0
 * @description
 * @date 2023/8/30
 */

public enum FileStorageEnum implements IBaseEnum<String> {

    LOCAL_STO("local","本地存储"),
    FAST_DFS_STO("fast_dfs","Fast-dfs"),
    ALI_OSS_STO("ali_oss","阿里云OSS"),

    ;

    @Getter
    private String value;

    @Getter
    private String name;

    FileStorageEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
