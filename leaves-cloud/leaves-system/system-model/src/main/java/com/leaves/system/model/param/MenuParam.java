package com.leaves.system.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author :leaves
 * @description :
 * @date 2022-04-15
 */

@Setter
@Getter
public class MenuParam {

    @ApiModelProperty("关键字(菜单名称)")
    private String keywords;
}
