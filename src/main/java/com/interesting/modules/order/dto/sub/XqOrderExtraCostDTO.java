package com.interesting.modules.order.dto.sub;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/9/18 14:51
 * @VERSION: V1.0
 */
@Data
public class XqOrderExtraCostDTO {

    @ApiModelProperty("主键")
    private String id;
    /**
     * 额外费用说明
     */
    @ApiModelProperty("额外费用说明")
    private String instruction;
    /**
     * 币种
     */
    @ApiModelProperty("币种")
    private String currency;
    /**
     * 金额
     */
    @ApiModelProperty("金额")
    private BigDecimal price;

    @ApiModelProperty("汇率")
    private BigDecimal exchangeRate;

    @ApiModelProperty("客户订单号")
    private String customerOrderNum;

    @ApiModelProperty("订单产品id")
    private String orderDetailId;

}
