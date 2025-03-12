package com.interesting.modules.workspace.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/4/17 19:18
 * @VERSION: V1.0
 */
@Data
public class XqWorkspaceCatalogDTO {

    @ApiModelProperty("主键")
    private String id;


    @ApiModelProperty("主键")
    private String pId;

    /**
     * 所属项目id
     */
    @ApiModelProperty(value = "用户角色",hidden = true)
    private String roleId;
    /**
     * 目录名称
     */
    @ApiModelProperty(value = "目录名称",required = true)
    @NotBlank(message = "目录名称不可为空")
    private String catalogName;

    /**
     * 是否空运 0否 1是
     */
    @ApiModelProperty("是否空运 0否 1是")
    private Integer izAir;

}
