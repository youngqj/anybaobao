package com.interesting.modules.workspace.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/11/13 16:22
 * @VERSION: V1.0
 */

@Data
public class XqWorkspaceFileDTO {

    @ApiModelProperty(value = "文件名称",required = true)
    @NotBlank(message = "文件名称不可为空")
    private String fileName;

    @ApiModelProperty(value = "文件类型",required = true)
    @NotBlank(message = "文件类型不可为空")
    private String fileType;

    @ApiModelProperty(value = "文件大小",required = true)
    @NotBlank(message = "文件大小不可为空")
    private String fileSize;

    @ApiModelProperty(value = "附件相对路径",required = true)
    @NotBlank(message = "附件相对路径不可为空")
    private String fileUrl;

}
