package com.interesting.modules.orderconfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class XqOrderConfigDto {

    @ApiModelProperty(value = "主键")
    private String id;
    @ApiModelProperty(value = "中信保费率")
    private String creditInsuranceRate;
    @ApiModelProperty(value = "保理利息费率")
    private String factoringInterestRate;
    @ApiModelProperty(value = "保理天数")
    private Integer days;
}
