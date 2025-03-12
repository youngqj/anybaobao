package com.interesting.modules.overseas.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.common.annotation.Excel;
import com.interesting.config.BigDecimalSerializer;
import com.interesting.modules.reportform.vo.SumVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SumRemmitAmountVO extends SumVO {

    @Excel(name = "出库金额", width = 15)
    @ApiModelProperty(value = "总出库金额")
    private BigDecimal totalExitMoney;

    @Excel(name = "应收总额", width = 15)
    @ApiModelProperty(value = "未收汇金额")
    private BigDecimal outstandingReceivables;

    @Excel(name = "信保保险费（CNY）", width = 15)
    @ApiModelProperty(value = "总信保费")
    private BigDecimal totalCreditInsurancePremium;

    @Excel(name = "信保保险费（USD）", width = 15)
    @ApiModelProperty(value = "总信保费转换")
    private BigDecimal totalCreditInsurancePremiumConvert;

    @Excel(name = "保理利息", width = 15)
    @ApiModelProperty(value = "总保理利息")
    private BigDecimal totalFactoringInterest;
}
