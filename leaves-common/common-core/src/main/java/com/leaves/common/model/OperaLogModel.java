package com.leaves.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志记录
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="OperaLogModel对象", description="操作日志记录")
public class OperaLogModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日志主键")
    private String id;

    @ApiModelProperty(value = "模块标题")
    private String title;

    @ApiModelProperty(value = "方法名称")
    private String method;

    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    @ApiModelProperty(value = "操作人员")
    private String operationUser;

    @ApiModelProperty(value = "请求URL")
    private String operationUrl;

    @ApiModelProperty(value = "主机地址")
    private String operationIp;

    @ApiModelProperty(value = "操作地点")
    private String operationLocation;

    @ApiModelProperty(value = "请求参数")
    private String operationParam;

    @ApiModelProperty(value = "返回参数")
    private String result;

    @ApiModelProperty(value = "操作状态（1正常 0异常）")
    private Integer status;

    @ApiModelProperty(value = "错误消息")
    private String errorMsg;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime operationTime;


}
