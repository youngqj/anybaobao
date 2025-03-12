package com.interesting.modules.orderdetail.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class XqCommissionOrderVO {
    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "订单详情Id")
    private String orderDetailId;

    @ApiModelProperty(value = "佣金详情Id")
    private String commissionId;

    @ApiModelProperty(value = "产品id")
    private String productId;

    @ApiModelProperty(value = "品类")
    private String productCategory;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "产品英文名称")
    private String productNameEn;

    @ApiModelProperty(value = "产品规格")
    private String productSpecs;

    @ApiModelProperty(value = "产品等级")
    private String productGrade;

    /**
     * 版面要求
     */
    @ApiModelProperty(value = "版面要求")
    private String layoutRequirements;


    /**
     * 包装方式
     */
    @ApiModelProperty(value = "包装方式质量id")
    private String packagingUnit;

    @ApiModelProperty(value = "包装方式")
    private String packaging;

    @ApiModelProperty(value = "佣金")
    private BigDecimal commissionPrice;


}
