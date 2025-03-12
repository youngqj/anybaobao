package com.interesting.modules.order.vo.sub;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class XqOrderProdVO {
    /**主键*/
    @ApiModelProperty(value = "主键")
    private String id;
    /**订单表id*/
    @ApiModelProperty(value = "订单表id")
    private String orderId;
    /**产品名称*/
    @ApiModelProperty(value = "产品id")
    private String productId;
    /**产品名称*/
    @ApiModelProperty(value = "产品品类", required = true)
    private String productCategoryName;
    @ApiModelProperty(value = "真~产品品类")
    private String trueProductCategoryName;
    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String productName;
    @ApiModelProperty(value = "产品规格")
    private String productSpecs;
    /**产品英文名称*/
    @ApiModelProperty(value = "产品英文名称")
    private String productNameEn;
    /**国内HS编码*/
    @ApiModelProperty(value = "国内HS编码")
    private String hsCodeDomestic;
    /**国外HS编码*/
    @ApiModelProperty(value = "国外HS编码")
    private String hsCodeForeign;
    /**国外关税率*/
    @ApiModelProperty(value = "国外关税率")
    private String foreignTariff;
    /**包装方式*/
    @ApiModelProperty(value = "包装方式")
    private String packaging;

    @ApiModelProperty(value = "等级")
    private String productGrade;
    /**包装方式*/
    @ApiModelProperty(value = "包装方式质量id")
    private String packagingUnit;
    /**包装方式*/
    @ApiModelProperty(value = "包装方式质量")
    private String packagingUnitTxt;
    /**每箱重量id*/
    @ApiModelProperty(value = "每箱重量")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal weightPerBox;
    /**每箱重量*/
    @ApiModelProperty(value = "每箱重量单位id")
    private String weightPerBoxUnit;
    /**每箱重量*/
    @ApiModelProperty(value = "每箱重量单位")
    private String weightPerBoxUnitTxt;
    /**总箱数*/
    @ApiModelProperty(value = "总箱数")
    private Integer totalBoxes;
    /**总重*/
    @JsonSerialize(using = BigDecimalSerializer.class)
    @ApiModelProperty(value = "总重")
    private java.math.BigDecimal totalWeight;
    /**单价/箱*/
    @JsonSerialize(using = BigDecimalSerializer.class)
    @ApiModelProperty(value = "单价/箱")
    private java.math.BigDecimal pricePerBox;
    /**单价/磅*/
    @JsonSerialize(using = BigDecimalSerializer.class)
    @ApiModelProperty(value = "单价/磅")
    private java.math.BigDecimal pricePerLb;
    /**销售金额*/
    @JsonSerialize(using = BigDecimalSerializer.class)
    @ApiModelProperty(value = "销售金额")
    private java.math.BigDecimal salesAmount;
    /**特殊情况备注*/
    @ApiModelProperty(value = "特殊情况备注")
    private String specialNotes;
    @ApiModelProperty("币种")
    private String currency;

    /**版面要求*/
    @ApiModelProperty(value = "版面要求")
    private String layoutRequirements;

    /**
     * 明细汇率
     */
    @ApiModelProperty(value = "明细汇率")
    private java.math.BigDecimal detailExchangeRate;

}
