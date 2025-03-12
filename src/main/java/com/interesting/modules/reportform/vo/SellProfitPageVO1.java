package com.interesting.modules.reportform.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer2;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SellProfitPageVO1 {
    @ApiModelProperty(value = "订单详情id")
    private String orderDetailId;


    /**
     * 总费用除以销售数量
     */
    @ApiModelProperty(value = "总费用除以销售数量")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalAmount;


}
