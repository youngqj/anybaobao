package com.interesting.modules.reportform.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.common.annotation.Excel;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SumFinanceReportVO extends SumVO{

    @Excel(name = "合计收汇金额")
    @ApiModelProperty(value = "合计收汇金额")
    private BigDecimal sumShje = BigDecimal.ZERO;

    @ApiModelProperty(value = "报关金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal bgjeFob = BigDecimal.ZERO;


    @Excel(name = "报关金额CFR")
    @ApiModelProperty(value = "报关金额CFR")
    private BigDecimal bgjeCfr;


}
