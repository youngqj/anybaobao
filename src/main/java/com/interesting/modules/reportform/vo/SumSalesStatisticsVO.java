package com.interesting.modules.reportform.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.common.annotation.Excel;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/12/21 13:48
 * @VERSION: V1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SumSalesStatisticsVO extends SumVO {


    @Excel(name = "额外费用",width = 10)
    @ApiModelProperty(value = "额外费用")
    private BigDecimal extraFree = BigDecimal.ZERO;
    @Excel(name = "订单扣除佣金",width = 10)
    @ApiModelProperty(value = "佣金")
    private BigDecimal amount = BigDecimal.ZERO;
    @Excel(name = "折扣/其他",width = 10)
    @ApiModelProperty(value = "其他")
    private BigDecimal other = BigDecimal.ZERO;
//
//    @ApiModelProperty("币种")
//    private String currency;

    @Excel(name = "应收总额", width = 15)
    @ApiModelProperty(value = "销售总额-订单扣除")
    private BigDecimal totalSalesAmountSuddkc = BigDecimal.ZERO;


    @Excel(name = "信保保险费", width = 15)
    @ApiModelProperty("信保保险费")
    private BigDecimal totalCreditInsurancePremium = BigDecimal.ZERO;

    @Excel(name = "保理利息", width = 15)
    @ApiModelProperty("保理利息")
    private BigDecimal totalFactoringInterest = BigDecimal.ZERO;

    @Excel(name = "实收总金额", width = 15)
    @ApiModelProperty("实收总金额")
    private BigDecimal totalRealReceiveAmount = BigDecimal.ZERO;

    @Excel(name = "未收款金额", width = 15)
    @ApiModelProperty("未收款金额")
    private BigDecimal totalUnReceiveAmount = BigDecimal.ZERO;


}
