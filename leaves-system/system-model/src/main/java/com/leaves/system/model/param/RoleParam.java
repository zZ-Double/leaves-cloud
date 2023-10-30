package com.leaves.system.model.param;

import com.leaves.common.base.BaseParam;
import com.leaves.common.enums.StatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author :leaves
 * @description :
 * @date 2022-04-15
 */

@Setter
@Getter
public class RoleParam implements Serializable {

    @ApiModelProperty(value = "角色ID")
    @NotBlank(message = "id不能为空，请检查id参数", groups = {edit.class})
    private String id;

    @ApiModelProperty(value = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    @ApiModelProperty(value = "角色编码")
    @NotBlank(message = "角色编码不能为空")
    private String roleCode;

    @ApiModelProperty(value = "数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
    @NotNull(message = "数据权限不能为空")
    private Integer dataScope;

    @ApiModelProperty(value = "角色状态")
    private StatusEnum status;


    /**
     * 参数校验分组：编辑
     */
    public @interface edit {
    }
}
