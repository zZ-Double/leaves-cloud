package com.leaves.system.model.param;

import com.leaves.common.base.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author :leaves
 * @description :
 * @date 2022-04-15
 */

@Setter
@Getter
public class PermParam extends BaseParam {

    @ApiModelProperty(value = "权限ID")
    @NotBlank(message = "id不能为空，请检查id参数", groups = {delete.class, detail.class, edit.class})
    private String id;

    @ApiModelProperty(value = "权限名称")
    @NotBlank(message = "权限名称不能为空", groups = {add.class, edit.class})
    private String perName;

    @ApiModelProperty(value = "权限标识")
    @NotBlank(message = "权限标识不能为空", groups = {add.class, edit.class})
    private String perCode;

    @ApiModelProperty(value = "请求地址")
    private String url;
}
