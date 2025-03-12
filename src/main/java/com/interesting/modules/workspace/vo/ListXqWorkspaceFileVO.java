package com.interesting.modules.workspace.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/11/13 15:30
 * @VERSION: V1.0
 */
@Data
public class ListXqWorkspaceFileVO {


    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("目录id")
    private String catalogId;

    @ApiModelProperty("目录名")
    private String catalogName;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("文件类型")
    private String fileType;

    @ApiModelProperty("文件大小")
    private String fileSize;

    @ApiModelProperty("附件相对路径")
    private String fileUrl;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

}
