package com.interesting.modules.files.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ListXqFileVO {
    @ApiModelProperty(value = "主键id")
    private String id;

//    @ApiModelProperty(value = "订单id")
//    private String orderId;
//
//    @ApiModelProperty(value = "模块类型")
//    private String moduleType;

    @ApiModelProperty(value = "序号")
    private String sortNum;

    @ApiModelProperty(value = "树形节点id")
    private String nodeId;

    @ApiModelProperty(value = "文件路径")
    private String fileUrl;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "目录名")
    private String categoryName;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "文件大小")
    private Integer fileSize;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private String fileUid;
}
