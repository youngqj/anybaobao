package com.interesting.modules.order.dto.sub;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 产品信息
 */
@Data
public class AddOrderProdDTO {
    private String id;


    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品Id", required = true)
    @NotBlank(message = "订单中产品Id不能为空")
    private String productId;

    /**产品名称*/
    @ApiModelProperty(value = "产品名称", required = true)
    @NotBlank(message = "订单中产品名称不能为空")
    private String productName;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品品类名称", required = true)
    private String productCategoryName;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品规格", required = true)
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

    @ApiModelProperty(value = "包装方式单位id")
    private String packagingUnit;


    @ApiModelProperty(value = "等级")
    private String productGrade;


    /**每箱重量*/
    @ApiModelProperty(value = "每箱重量", required = true)
    @NotNull(message = "每箱重量不能为空")
    private java.math.BigDecimal weightPerBox;

    @ApiModelProperty(value = "每箱重量单位id")
    private java.math.BigDecimal weightPerBoxUnit;

    /**总箱数*/
    @ApiModelProperty(value = "总箱数", required = true)
    @NotNull(message = "总箱数不能为空")
    private Integer totalBoxes;

    /**总重*/
    @ApiModelProperty(value = "总重", required = true)
    @NotNull(message = "总重不能为空")
    private java.math.BigDecimal totalWeight;

    /**单价/箱*/
    @ApiModelProperty(value = "单价/箱", required = true)
    //@NotNull(message = "单价/箱不能为空")
    private java.math.BigDecimal pricePerBox;

    /**单价/磅*/
    @ApiModelProperty(value = "单价/磅", required = true)
    @NotNull(message = "单价/磅不能为空")
    private java.math.BigDecimal pricePerLb;

    /**销售金额*/
    @ApiModelProperty(value = "销售金额", required = true)
    @NotNull(message = "销售金额不能为空")
    private java.math.BigDecimal salesAmount;

    /**特殊情况备注*/
    @ApiModelProperty(value = "特殊情况备注")
    private String specialNotes;

    @ApiModelProperty("币种")
//    @NotNull(message = "币种不能为空")
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
