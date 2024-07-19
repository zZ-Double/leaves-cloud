package com.leaves.system.model.param;

import com.leaves.common.base.BaseParam;
import com.leaves.common.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author leaves
 * @version 1.0
 * @description
 * @date 2024/7/19
 */
@Setter
@Getter
public class TenantParam extends BaseParam {

    // 状态
    private StatusEnum status;
}
