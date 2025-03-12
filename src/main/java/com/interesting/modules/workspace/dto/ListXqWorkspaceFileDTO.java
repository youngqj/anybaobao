package com.interesting.modules.workspace.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/11/13 14:58
 * @VERSION: V1.0
 */
@Data
public class ListXqWorkspaceFileDTO {

    @ApiModelProperty(value = "目录id",required = true)
    @NotBlank(message = "目录不可为空")
    private String catalogId;

    @ApiModelProperty("文件名")
    private String fileName;

    @ApiModelProperty("录入人id")
    private String enterPersonId;


}
