package com.interesting.modules.reportform.vo;

import com.interesting.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/12/20 11:53
 * @VERSION: V1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SumFactoryFinanceVO extends SumVO{
//
//    @ApiModelProperty("币种")
//    private String currency;

    @Excel(name = "采购总金额")
    @ApiModelProperty("采购总金额")
    private BigDecimal totalPurchaseAmount = BigDecimal.ZERO;

    @Excel(name = "实付金额")
    @ApiModelProperty("实付金额")
    private BigDecimal totalPaymentAmount = BigDecimal.ZERO;

    @Excel(name = "尚欠金额")
    @ApiModelProperty("尚欠金额")
    private BigDecimal totalSqye = BigDecimal.ZERO;

    @Excel(name = "工厂扣款额")
    @ApiModelProperty("工厂扣款额")
    private BigDecimal totalGckke = BigDecimal.ZERO;



}
