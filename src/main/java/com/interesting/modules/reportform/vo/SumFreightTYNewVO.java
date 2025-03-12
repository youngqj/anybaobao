package com.interesting.modules.reportform.vo;

import com.interesting.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class SumFreightTYNewVO extends SumVO{

    @Excel(name = "价格合计",width = 10)
    @ApiModelProperty(value = "价格")
    private BigDecimal priceTotal;

    @Excel(name = "财务确认金额合计",width = 15)
    @ApiModelProperty(value = "财务确认金额")
    private BigDecimal financeAmountTotal;


}
