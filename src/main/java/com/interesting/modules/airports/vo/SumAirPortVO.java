package com.interesting.modules.airports.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SumAirPortVO {
    @ApiModelProperty(value = "国内申请费用RMB")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal totalGnfyAmount;
    @ApiModelProperty(value = "国内汇款费用RMB")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal totalGnhkfyAmount;
    @ApiModelProperty(value = "国外申请费用USD")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal totalGwfyAmount;
    @ApiModelProperty(value = "国外汇款费用USD")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal totalGwhkfyAmount;
}
