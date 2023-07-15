package com.leaves.common.base;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 基础请求参数
 */
@Setter
@Getter
public class BaseParam implements Serializable {

    private static final long serialVersionUID = -1308025011022365639L;


    private static final Pattern PATTERN_SORT = Pattern.compile(
            "^(?<field>[a-zA-Z0-9]+)(?<order>asc|desc)$",
            Pattern.CASE_INSENSITIVE);

    /**
     * 分页页码
     */
    @ApiModelProperty(value = "分页页码")
    private Integer current;

    /**
     * 每页数量
     */
    @ApiModelProperty(value = "每页数量")
    private Integer size;

    /**
     * 排序字段
     */
    @Setter(AccessLevel.NONE)
    private String sortField;

    /**
     * 排序顺序（null, asc, desc）
     */
    @Setter(AccessLevel.NONE)
    private String sortOrder;

    /**
     * 排序原始字符串
     */
    @Setter(AccessLevel.NONE)
    private String sortString;

    /**
     * 参数校验分组：列表
     */
    public @interface list {
    }

    /**
     * 参数校验分组：分页
     */
    public @interface page {
    }

    /**
     * 参数校验分组：增加
     */
    public @interface add {
    }

    /**
     * 参数校验分组：编辑
     */
    public @interface edit {
    }

    /**
     * 参数校验分组：删除
     */
    public @interface delete {
    }

    /**
     * 参数校验分组：详情
     */
    public @interface detail {
    }


    /**
     * 解析排序字段及顺序
     */
    public void setSort(String sortString) {

        this.sortField = null;
        this.sortOrder = null;
        this.sortString = null;

        if (StrUtil.isEmptyIfStr(sortString))
            return;

        Matcher matcher = PATTERN_SORT.matcher(sortString);

        // 不匹配的，按asc拼接后缀，再次尝试匹配
        if (!matcher.matches())
            matcher = PATTERN_SORT.matcher(sortString + "asc");

        if (!matcher.matches())
            return;

        // 全部小写
        this.sortField = StrUtil.toUnderlineCase(matcher.group("field"));
        this.sortOrder = matcher.group("order").toLowerCase();
        this.sortString = sortString;
    }
}
