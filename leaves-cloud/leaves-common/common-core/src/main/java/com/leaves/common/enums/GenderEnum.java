package com.leaves.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.leaves.common.base.IBaseEnum;
import lombok.Getter;


public enum GenderEnum implements IBaseEnum<Integer>, IEnum<Integer> {

    FEMALE(0, "女性"),
    MALE(1, "男性"),
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
