package com.interesting.modules.notes.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class XqProblemNoteVO {
    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "模块类型")
    private String moduleType;

    @ApiModelProperty(value = "面单id")
    private String orderId;

    @ApiModelProperty(value = "问题说明")
    private String note;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
