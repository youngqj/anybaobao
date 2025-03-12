package com.interesting.modules.prepayments.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class XqPrepaymentsDto {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "预付金额")
    private BigDecimal prepaymentAmount;

    @ApiModelProperty(value = "已付金额")
    private BigDecimal paidAmount;

    @ApiModelProperty(value = "剩余金额")
    private BigDecimal remainderAmount;

    @ApiModelProperty(value = "备注")
    private String remark;
}
