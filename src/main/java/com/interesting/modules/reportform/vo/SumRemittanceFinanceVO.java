package com.interesting.modules.reportform.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.common.annotation.Excel;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class SumRemittanceFinanceVO extends SumVO{

//    @ApiModelProperty("币种")
//    private String currency;

    @Excel(name = "保理金额")
    @ApiModelProperty(value = "保理金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal totalFactoringMoney = BigDecimal.ZERO;

    @Excel(name = "保理利息")
    @ApiModelProperty(value = "保理利息")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal totalFactoringInterest = BigDecimal.ZERO;

    @Excel(name = "收汇金额")
    @ApiModelProperty(value = "收汇金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal totalRemittanceAmount= BigDecimal.ZERO;

    @Excel(name = "收汇总合计")
    @ApiModelProperty(value = "收汇总合计")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal totalRemittanceAmountAddFactoringMoney = BigDecimal.ZERO;

}
