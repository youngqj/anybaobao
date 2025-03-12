package com.interesting.modules.files.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class InstOrUpdtXqFilesDTO {
    @ApiModelProperty(value = "编辑时传")
    private String id;

//    @ApiModelProperty(value = "模块类型", hidden = true)
//    private String moduleType;

    @ApiModelProperty(value = "文件路径", required = true)
    @NotBlank(message = "文件路径不能为空")
    private String fileUrl;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "文件大小")
    private Integer fileSize;

    private String fileUid;

    private String nodeId;
}
