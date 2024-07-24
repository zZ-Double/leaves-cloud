package com.leaves.system.model.param;

import com.leaves.common.base.BaseParam;
import com.leaves.common.enums.DataScopeEnum;
import com.leaves.common.enums.GenderEnum;
import com.leaves.common.enums.StatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @version v1.0.0
 * @author: leaves
 * @description: 用户操作请求参数
 * @createTime: 2022-04-14
 */
@Setter
@Getter
public class UserParam extends BaseParam {

    // 状态
    private StatusEnum status;

    // 部门ID
    private String deptId;

    private String tenantId;

}
