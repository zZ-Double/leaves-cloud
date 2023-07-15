package com.leaves.system.model.param;

import com.leaves.common.base.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @version v1.0.0
 * @author: leaves
 * @description: 用户操作请求参数
 * @createTime: 2022-04-14
 */
@Setter
@Getter
public class UserParam extends BaseParam {

    @ApiModelProperty(value = "用户ID")
    @NotBlank(message = "id不能为空，请检查id参数", groups = {delete.class, detail.class, edit.class})
    private String id;

    @ApiModelProperty(value = "登录账号")
    @NotBlank(message = "用户登录帐号不能为空", groups = {add.class, edit.class})
    private String userName;

    @ApiModelProperty(value = "手机号码")
    @NotBlank(message = "手机号码不能为空", groups = {add.class, edit.class})
    private String phoneNumber;

    @ApiModelProperty(value = "用户性别（0男 1女 2未知）")
    @NotBlank(message = "用户性别不能为空", groups = {add.class, edit.class})
    private String sex;

    @ApiModelProperty(value = "头像路径")
    private String avatar;

    @ApiModelProperty(value = "角色Id集合，以逗号分隔（1,2,3,4）")
    private String roleIds;

}
