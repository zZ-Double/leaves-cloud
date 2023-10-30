package com.leaves.system.model.param;

import com.leaves.common.base.BaseParam;
import com.leaves.common.enums.StatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @version v1.0.0
 * @author: leaves
 * @description:
 * @createTime: 2022-05-15
 */
@Setter
@Getter
public class DeptParam extends BaseParam {

    @ApiModelProperty(value = "部门ID")
    @NotBlank(message = "id不能为空，请检查id参数", groups = {delete.class, detail.class, edit.class})
    private String id;

    @ApiModelProperty(value = "部门名称")
    @NotBlank(message = "部门名称不能为空", groups = {add.class, edit.class})
    private String deptName;

    @ApiModelProperty(value = "父级部门id")
    @NotBlank(message = "父级部门id", groups = {add.class, edit.class})
    private String parentId;

    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;

    @ApiModelProperty(value = "负责人")
    private String leader;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "部门状态（1正常 0停用）")
    private StatusEnum status;


}
