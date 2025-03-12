package com.interesting.modules.remittance.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2024/1/3 15:06
 * @VERSION: V1.0
 */
@Data
public class QueryDataNotInDetailVO {

    @ApiModelProperty("订单id")
    private String id;

    @ApiModelProperty("订单名称")
    private String orderNum;

    @ApiModelProperty("客户订单号")
    private String customerOrderNum;

    @ApiModelProperty("最小收汇日期")
    private Date remittanceExpireDate;
}
