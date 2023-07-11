package com.leaves.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志记录
 *
 * @TableName sys_operation_log
 */
@TableName(value = "sys_operation_log")
@Data
public class SysOperationLog implements Serializable {
    /**
     * 日志主键
     */
    @TableId
    private String id;

    /**
     * 模块标题
     */
    private String title;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 操作人员
     */
    private String operationUser;

    /**
     * 请求URL
     */
    private String operationUrl;

    /**
     * 主机地址
     */
    private String operationIp;

    /**
     * 操作地点
     */
    private String operationLocation;

    /**
     * 请求参数
     */
    private String operationParam;

    /**
     * 返回参数
     */
    private String result;

    /**
     * 操作状态
     */
    private Integer status;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 操作时间
     */
    private Date operationTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}