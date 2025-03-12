package com.interesting.modules.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/9/15 9:38
 * @VERSION: V1.0
 */
@Data
public class ListXqOrderTransferRecordVO {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("转让发起人")
    private String transferFrom;

    @ApiModelProperty("转让目标")
    private String transferTo;

    @ApiModelProperty("转让时间")
    private Date createTime;


}
