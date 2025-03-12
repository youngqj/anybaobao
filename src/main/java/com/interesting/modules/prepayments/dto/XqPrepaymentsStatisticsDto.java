package com.interesting.modules.prepayments.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class XqPrepaymentsStatisticsDto {

    @ApiModelProperty(value = "预付金额")
    private BigDecimal prepaymentAmount;

    @ApiModelProperty(value = "已付金额")
    private BigDecimal paidAmount;

    @ApiModelProperty(value = "剩余金额")
    private BigDecimal remainderAmount;

    @ApiModelProperty(value = "币种")
    private String currency;
}
