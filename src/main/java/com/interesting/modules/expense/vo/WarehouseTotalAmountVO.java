package com.interesting.modules.expense.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/3/20 10:52
 * @Project: trade-manage
 * @Description:
 */

@Data
public class WarehouseTotalAmountVO {

    @ApiModelProperty(value = "费用USD")
    private BigDecimal expenseTotal = BigDecimal.ZERO;

    @ApiModelProperty(value = "付款金额")
    private BigDecimal paymentAmountTotal = BigDecimal.ZERO;

    @ApiModelProperty(value = "财务审核金额")
    private BigDecimal auditAmountTotal = BigDecimal.ZERO;

    @ApiModelProperty(value = "未付金额")
    private BigDecimal notPaymentAmountTotal = BigDecimal.ZERO;
}
