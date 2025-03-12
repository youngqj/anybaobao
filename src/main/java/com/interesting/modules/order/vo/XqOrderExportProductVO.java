package com.interesting.modules.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class XqOrderExportProductVO {
    @ApiModelProperty(value = "主键id")
    private String id;
    @ApiModelProperty(value = "英文品名")
    private String productName;
    @ApiModelProperty(value = "重量")
    private BigDecimal singleWeight ;
    @ApiModelProperty("重量单位")
    private String weightUnitTxt;
    @ApiModelProperty(value = "销售单价USD/LB")
    private BigDecimal unitPrice;
    @ApiModelProperty(value = "销售总额")
    private BigDecimal singleAmount;
    @ApiModelProperty(value = "币种")
    private String currency;
    @ApiModelProperty(value = "包装方式")
    private String packaging;
    @ApiModelProperty("包装方式单位")
    private String packagingUnitTxt;
    @ApiModelProperty(value = "总箱数CS")
    private BigDecimal totalBoxes;
    @ApiModelProperty(value = "采购数量")
    private BigDecimal cgWeight;
    @ApiModelProperty(value = "采购数量（KGS）")
    private BigDecimal cgWeightKgs;
    @ApiModelProperty(value = "采购数量（LBS）")
    private BigDecimal cgWeightLbs;
    @ApiModelProperty(value = "采购单位：1 kg  2 lbs")
    private Integer cgWeightUnit;
    @ApiModelProperty(value = "采购分摊数量（KGS）")
    private BigDecimal ftWeightKgs;
    @ApiModelProperty(value = "采购分摊数量（LBS）")
    private BigDecimal ftWeightLbs;

    @ApiModelProperty(value = "体积")
    private String volume;


//
//    @ApiModelProperty(value = "销售总价USD")
//    private String salesAmount;

//    @ApiModelProperty(value = "总磅数/LBS")
//    private String totalWeight;
}
