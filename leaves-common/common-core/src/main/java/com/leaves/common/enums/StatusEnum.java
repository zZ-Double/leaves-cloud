package com.leaves.common.enums;

import com.leaves.common.base.IBaseEnum;
import lombok.Getter;


public enum StatusEnum implements IBaseEnum<Integer> {

    DISABLE (0, "禁用"), // false
    ENABLE(1, "启用"), // true

    ;

    @Getter
    private Integer value;

    @Getter
    private String name;

    StatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
