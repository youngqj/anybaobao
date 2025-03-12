package com.interesting.modules.commission.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EditCommissionDistributionDTO {
    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "订单详情Id")
    private String orderDetailId;

    @ApiModelProperty(value = "产品id")
    private String productId;

    @ApiModelProperty(value = "佣金金额")
    private BigDecimal commissionPrice;
}
