package com.leaves.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.leaves.common.base.IBaseEnum;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusEnum implements IBaseEnum<Integer> {

    DISABLE (0, "禁用"), // false
    ENABLE(1, "启用"), // true

    ;

    @Getter
    @EnumValue
    private Integer value;

    @Getter
    private String name;

    StatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
