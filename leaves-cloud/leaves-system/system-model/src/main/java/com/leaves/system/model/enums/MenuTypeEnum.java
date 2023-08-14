package com.leaves.system.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.leaves.common.base.IBaseEnum;
import lombok.Getter;

public enum MenuTypeEnum implements IBaseEnum<Integer> {

    NULL(0, null),
    CATALOG(1, "目录"),
    MENU(2, "菜单"),
    BUTTON(3, "按钮"),
    EXT_LINK(4, "外链"),

    ;

    @Getter
    @EnumValue //  Mybatis-Plus 提供注解表示插入数据库时插入该值
    private Integer value;

    @Getter
    private String name;

    MenuTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }


}
