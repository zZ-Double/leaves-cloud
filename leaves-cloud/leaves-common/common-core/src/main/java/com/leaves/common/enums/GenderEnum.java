package com.leaves.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.leaves.common.base.IBaseEnum;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GenderEnum implements IBaseEnum<Integer> {

    FEMALE(0, "女"),
    MALE(1, "男"),
    UN_KNOW(2, "未知"),

    ;

    @Getter
    @EnumValue
    private Integer value;

    @Getter
    private String name;

    GenderEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
