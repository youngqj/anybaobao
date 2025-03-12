package com.interesting.modules.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("xq_product_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqProductInfo implements Serializable {

    private static final long SerialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "产品名称")
    private String productName;

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

    @ApiModelProperty(value = "产品规格")
    private String productSpecs;

    @ApiModelProperty(value = "等级")
    private String productGrade;

    @ApiModelProperty(value = "品类")
    private String productCategory;

    /**国外关税率*/
    @ApiModelProperty(value = "国外关税率")
    private String foreignTariff;

//    /**包装方式*/
//    @ApiModelProperty(value = "包装方式质量id")
//    private String packagingUnit;
//
//    private BigDecimal packaging;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除 0否 1是")
    private Integer izDelete;
}
