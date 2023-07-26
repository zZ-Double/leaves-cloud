package com.leaves.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.leaves.common.base.BaseEntity;
import com.leaves.common.enums.StatusEnum;
import com.leaves.system.model.enums.MenuTypeEnum;
import lombok.Data;

/**
 * 菜单管理
 *
 * @TableName sys_menu
 */
@TableName(value = "sys_menu")
@Data
public class SysMenu extends BaseEntity {


    /**
     * 父菜单ID
     */
    private String parentId;

    /**
     * 菜单类型(1：菜单；2：目录；3：外链；4：按钮)
     */
    private MenuTypeEnum type;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 路由路径(浏览器地址栏路径)
     */
    private String path;

    /**
     * 组件路径(vue页面完整路径，省略.vue后缀)
     */
    private String component;

    /**
     * 按钮权限标识
     */
    private String perm;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 跳转路径
     */
    private String redirect;

    /**
     * 状态（1正常 0停用）
     */
    private StatusEnum status;

    /**
     * 备注
     */
    private String remark;

}