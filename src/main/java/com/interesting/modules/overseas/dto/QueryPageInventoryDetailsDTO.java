package com.interesting.modules.overseas.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryPageInventoryDetailsDTO {
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    @ApiModelProperty("仓库id")
    private String warehouseId;

    @ApiModelProperty("产品id")
    private String productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty("仓库lot")
    private String warehouseLot;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "产品英文名称")
    private String productNameEn;

    @ApiModelProperty(value = "初始来源单据号")
    private String sourceRepNum;
}
