package com.interesting.modules.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Data
public class XqProductInfoVO {
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "产品名称")

    private String productName;
    @ApiModelProperty(value = "产品规格")
    private String productSpecs;
    @ApiModelProperty(value = "等级")
    private String productGrade;
    @ApiModelProperty(value = "产品英文名称")
    private String productNameEn;
    @ApiModelProperty(value = "品类")
    private String productCategory;

    @ApiModelProperty(value = "品类名称")
    private String productCategoryName;

    @ApiModelProperty(value = "国内HS编码")
    private String hsCodeDomestic;

    @ApiModelProperty(value = "国外HS编码")
    private String hsCodeForeign;

    /**包装方式*/
    @ApiModelProperty(value = "包装方式")
    private String packaging;

    /**包装方式*/
    @ApiModelProperty(value = "包装方式质量id")
    private String packagingUnit;

    /**包装方式*/
    @ApiModelProperty(value = "包装方式质量")
    private String packagingUnitTxt;

    /**国外关税率*/
    @ApiModelProperty(value = "国外关税率")
    private String foreignTariff;

    /**版面要求*/
    @ApiModelProperty(value = "版面要求")
    private String layoutRequirements;

    @ApiModelProperty("辅料分类id，多个以英文逗号分割")
    private String categoryId;

    /**
     * 总箱数
     */
    @ApiModelProperty(value = "总箱数")
    private Integer totalBoxes;
}
