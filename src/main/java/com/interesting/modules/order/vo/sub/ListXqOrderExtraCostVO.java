package com.interesting.modules.order.vo.sub;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/9/18 15:24
 * @VERSION: V1.0
 */
@Data
public class ListXqOrderExtraCostVO {

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

    @ApiModelProperty(value = "客户订单号")
    private String customerOrderNum;

    @ApiModelProperty("汇率")
    private BigDecimal exchangeRate;


    @ApiModelProperty("订单产品id")
    private String orderDetailId;
    @ApiModelProperty("产品id")
    private String productId;
    @ApiModelProperty(value = "产品名称")
    private String productName;
    @ApiModelProperty(value = "产品规格")
    private String productSpecs;
    @ApiModelProperty(value = "包装方式")
    private String packaging;
    @ApiModelProperty(value = "等级")
    private String productGrade;
    @ApiModelProperty(value = "版面要求")
    private String layoutRequirements;

}
