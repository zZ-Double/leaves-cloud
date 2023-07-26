package com.leaves.system.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.leaves.common.base.IBaseEnum;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MenuTypeEnum implements IBaseEnum<Integer> {

    NULL(0, null),
    MENU(1, "菜单"),
    CATALOG(2, "目录"),
    EXT_LINK(3, "外链"),
    BUTTON(4, "按钮"),

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
