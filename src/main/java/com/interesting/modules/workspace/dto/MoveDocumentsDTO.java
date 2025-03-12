package com.interesting.modules.workspace.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/11/13 16:32
 * @VERSION: V1.0
 */
@Data
public class MoveDocumentsDTO {

    @ApiModelProperty(value = "目录id",required = true)
    @NotBlank(message = "目录不可为空")
    private String catalogId;

    @ApiModelProperty(value = "文件id列表",required = true)
    @NotBlank(message = "文件列表不可为空")
    private String documentsIdList;

}
