package com.interesting.modules.overseas.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BatchInventoryPageVO {
    private String id;

    @ApiModelProperty("仓库lot")
    private String warehouseLot;

    @ApiModelProperty(value = "产品id")
    private String productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "产品英文名称")
    private String productNameEn;

    @ApiModelProperty(value = "库存数量")
    private Integer inventoryNum;

    @ApiModelProperty(value = "初始来源订单号")
    private String sourceRepNum;

    @ApiModelProperty("仓库id")
    private String warehouseId;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "包装方式单位")
    private String packaging;

    @ApiModelProperty(value = "包装方式单位(id)")
    private String packagingUnit;

    @ApiModelProperty(value = "包装方式单位")
    private String packagingUnitTxt;

    @ApiModelProperty(value = "产品规格")
    private String productSpecs;

    @ApiModelProperty("来自后台")
    private Integer izBackend = 1;
}
