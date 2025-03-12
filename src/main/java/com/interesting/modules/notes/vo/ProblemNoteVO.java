package com.interesting.modules.notes.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ProblemNoteVO {

    @ApiModelProperty(value = "主键id")
    private String noteId;

    @ApiModelProperty(value = "问题说明")
    private String note;

    @ApiModelProperty(value = "订单id")
    private String orderId;
}
