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
public class RoleParam extends BaseParam {

    @ApiModelProperty(value = "角色ID")
    @NotBlank(message = "id不能为空，请检查id参数", groups = {delete.class, detail.class, edit.class})
    private String id;

    @ApiModelProperty(value = "角色名称")
    @NotBlank(message = "角色名称不能为空", groups = {add.class, edit.class})
    private String roleName;

    @ApiModelProperty(value = "角色编码")
    @NotBlank(message = "角色编码不能为空", groups = {add.class, edit.class})
    private String roleCode;

    @ApiModelProperty(value = "数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
    private String dataScope;

    @ApiModelProperty(value = "权限Id集合，以逗号分隔（1,2,3,4）")
    private String permissionIds;

    @ApiModelProperty(value = "角色状态")
    private String status;
}
