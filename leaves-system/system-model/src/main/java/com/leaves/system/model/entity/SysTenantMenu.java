package com.leaves.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 租户和资源关联表
 * @TableName sys_tenant_menu
 */
@TableName(value ="sys_tenant_menu")
@Data
public class SysTenantMenu implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 
     */
    private String tenantId;

    /**
     * 
     */
    private String menuId;

    /**
     * 
     */
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public SysTenantMenu(String tenantId, String menuId) {
        this.tenantId = tenantId;
        this.menuId = menuId;
    }
}