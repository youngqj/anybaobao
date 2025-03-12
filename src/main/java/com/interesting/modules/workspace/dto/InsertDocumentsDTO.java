package com.interesting.modules.workspace.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/11/13 16:20
 * @VERSION: V1.0
 */
@Data
public class InsertDocumentsDTO {

    @ApiModelProperty(value = "目录id",required = true)
    @NotBlank(message = "目录不可为空")
    private String catalogId;

    @ApiModelProperty(value = "新增文件集合",required = true)
    @NotEmpty(message = "新增的文件不可为空")
    private List<XqWorkspaceFileDTO> xqWorkspaceFileDTOS;

}
