package com.leaves.common.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author leaves
 * @version 1.0
 * @description
 * @date 2023/8/12
 */
@ApiModel("下拉选项对象")
@Data
@NoArgsConstructor
public class Option<T> {

    public Option(T value, String label) {
        this.value = value;
        this.label = label;
    }

    public Option(T value, String label, List<Option> children) {
        this.value = value;
        this.label = label;
        this.children= children;
    }

    @ApiModelProperty("选项的值")
    private T value;

    @ApiModelProperty("选项的标签")
    private String label;

    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<Option> children;

}
