package com.interesting.modules.reportform.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SumOrderAscrFinanceVO extends SumVO{
//
//    @ApiModelProperty("币种")
//    private String currency;

    @ApiModelProperty(value = "含税金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal totalTaxIncludedAmount;
    @ApiModelProperty(value = "付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal totalFinanceAuditAmount;

    @ApiModelProperty(value = "未付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal totalUnFinanceAuditAmount;

    @ApiModelProperty(value = "订单数量")
    private Integer orderTotalQuantity;
}
