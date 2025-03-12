package com.interesting.modules.overseas.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryPageOverSeasInventoryDTO {
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    @ApiModelProperty(value = "仓库id", hidden = true)
    private String warehouseId;

    @ApiModelProperty("仓库lot")
    private String warehouseLot;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "产品id", hidden = true)
    private String productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;
}
