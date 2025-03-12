package com.interesting.modules.overseas.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class OverSeasInventoryTransferDetailRecordVO {
    private String id;

    @ApiModelProperty("仓库id")
    private String transferWarehouseId;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "初始来源单据号")
    private String sourceRepNum;

    @ApiModelProperty(value = "仓库lot")
    private String warehouseLot;

    @ApiModelProperty(value = "新仓库lot")
    private String newWarehouseLot;

    @ApiModelProperty(value = "产品id")
    private String productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "当前库存")
    private Integer currentInventory;

    @ApiModelProperty(value = "转移数量")
    private Integer transferInventory;

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
