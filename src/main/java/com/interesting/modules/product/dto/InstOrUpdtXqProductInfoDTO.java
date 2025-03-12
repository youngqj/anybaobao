package com.interesting.modules.product.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class InstOrUpdtXqProductInfoDTO {
    @ApiModelProperty(value = "编辑时传id")
    private String id;

    @ApiModelProperty(value = "产品名称", required = true)
    @NotBlank(message = "产品名称不能为空")
    private String productName;

    @ApiModelProperty(value = "产品规格")
    private String productSpecs;

    @ApiModelProperty(value = "等级")
    private String productGrade;

    @ApiModelProperty(value = "产品英文名称")
    private String productNameEn;

    @ApiModelProperty(value = "国内HS编码")
    private String hsCodeDomestic;

    @ApiModelProperty(value = "国外HS编码")
    private String hsCodeForeign;

    /**包装方式*/
    @ApiModelProperty(value = "包装方式质量id")
    private String packagingUnit;

    @ApiModelProperty(value = "包装方式")
    private String packaging;

    /**国外关税率*/
    @ApiModelProperty(value = "国外关税率")
    private String foreignTariff;

    @ApiModelProperty(value = "品类")
    private String productCategory;


}
