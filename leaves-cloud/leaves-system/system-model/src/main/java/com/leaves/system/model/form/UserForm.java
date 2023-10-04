package com.leaves.system.model.form;

import com.leaves.common.base.BaseParam;
import com.leaves.common.enums.DataScopeEnum;
import com.leaves.common.enums.GenderEnum;
import com.leaves.common.enums.StatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author leaves
 * @version 1.0
 * @description
 * @date 2023/8/24
 */

@Setter
@Getter
public class UserForm implements Serializable {

    @ApiModelProperty(value = "用户ID")
    @NotBlank(message = "id不能为空，请检查id参数", groups = {BaseParam.edit.class})
    private String id;

    @ApiModelProperty(value = "登录账号")
    @NotBlank(message = "用户登录帐号不能为空")
    private String username;

    @ApiModelProperty(value = "用户昵称")
    @NotBlank(message = "用户昵称不能为空")
    private String nickName;

    @ApiModelProperty(value = "手机号码")
    @NotBlank(message = "手机号码不能为空")
    private String phoneNumber;

    @ApiModelProperty(value = "用户性别（0女 1男 2未知）")
    @NotNull(message = "用户性别不能为空")
    private GenderEnum sex;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "头像路径")
    private String avatar;

    @ApiModelProperty(value = "角色Id集合，以逗号分隔（1,2,3,4）")
    private List<String> roleIds;

    @ApiModelProperty(value = "数据范围（1：全部数据权限 2：本部门及以下数据权限 3：本部门数据权限 4：本人数据）")
    private DataScopeEnum dataScope;

    @ApiModelProperty(value = "状态")
    private StatusEnum status;

    @ApiModelProperty(value = "部门ID")
    private String deptId;
}
