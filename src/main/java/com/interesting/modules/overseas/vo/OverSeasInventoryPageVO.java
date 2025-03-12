package com.interesting.modules.overseas.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OverSeasInventoryPageVO {
    private String id;

    @ApiModelProperty("仓库id")
    private String warehouseId;

//    @ApiModelProperty("仓库lot")
//    private String warehouseLot;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "产品id")
    private String productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "产品英文名称")
    private String productNameEn;

    @ApiModelProperty(value = "库存数量")
    private Integer inventoryNum;
}
