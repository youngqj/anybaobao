package com.interesting.modules.overseas.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class OverSeasInventoryCheckDetailRecordVO {
    private String id;

//    @ApiModelProperty("仓库id")
//    private String warehouseId;
//
//    @ApiModelProperty("仓库名称")
//    private String warehouseName;

    @ApiModelProperty(value = "初始来源单据号")
    private String sourceRepNum;

    @ApiModelProperty(value = "仓库lot")
    private String warehouseLot;

    @ApiModelProperty(value = "产品id")
    private String productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;


    @ApiModelProperty(value = "实存数量")
    @NotNull(message = "实存数量不能为空")
    private Integer realInventory;

    @ApiModelProperty(value = "当前库存")
    private Integer currentInventory;

    @ApiModelProperty(value = "盘点数量")
    private Integer checkInventory;

    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "包装方式")
    private String packaging;

    @ApiModelProperty(value = "包装规格")
    private String productSpecs;


    @ApiModelProperty(value = "包装方式单位(id)")
    private String packagingUnit;

    @ApiModelProperty(value = "包装方式单位")
    private String packagingUnitTxt;

}
