package com.interesting.modules.commission.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AddCommissionDistributionDTO {
    @ApiModelProperty(value = "主键id")
    private String id;

    /**
     * 订单表id
     */
    @ApiModelProperty(value = "订单表id")
    private String orderId;

    @ApiModelProperty(value = "订单详情Id")
    private String orderDetailId;

    @ApiModelProperty(value = "产品id")
    private String productId;

    @ApiModelProperty(value = "佣金金额")
    private BigDecimal commissionPrice;


}
