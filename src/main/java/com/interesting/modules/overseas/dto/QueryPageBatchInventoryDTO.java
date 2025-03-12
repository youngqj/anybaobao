package com.interesting.modules.overseas.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class QueryPageBatchInventoryDTO {

    private Integer pageNo = 1;
    private Integer pageSize = 10;

    @ApiModelProperty(value = "仓库id", required = true)
    private String warehouseId;

    @ApiModelProperty(value = "产品id", required = true)
    private String productId;

    @ApiModelProperty("仓库lot")
    private String warehouseLot;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "产品英文名称")
    private String productNameEn;

    @ApiModelProperty(value = "初始来源订单号")
    private String sourceRepNum;

}
