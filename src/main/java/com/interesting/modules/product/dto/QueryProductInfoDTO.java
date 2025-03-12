package com.interesting.modules.product.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryProductInfoDTO {
    private Integer pageNo = 1;

    private Integer pageSize = 10;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "国内HS编码")
    private String hsCodeDomestic;

    @ApiModelProperty(value = "国外HS编码")
    private String hsCodeForeign;

    @ApiModelProperty(value = "产品品类")
    private String productCategory;

    @ApiModelProperty(value = "产品规格")
    private String productSpecs;


    @ApiModelProperty(value = "包装方式")
    private String packaging;

    @ApiModelProperty(value = "包装单位")
    private String packagingUnit;
}
