package com.leaves.system.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.leaves.common.enums.StatusEnum;
import com.leaves.system.model.enums.MenuTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel("菜单视图对象")
@Data
public class MenuVO implements Serializable {

    private String id;

    private String parentId;

    private String name;

    private String icon;

    private String path;

    private String component;

    private Integer sort;

    private StatusEnum status;

    private String redirect;

    private MenuTypeEnum type;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<MenuVO> children;

    private String perm;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private List<String> roles;

}
