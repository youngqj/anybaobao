package com.interesting.modules.overseas.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryPageRelativeLotDTO {
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    @ApiModelProperty("仓库名称")
    private String warehouseName;
    @ApiModelProperty(value = "产品名称")
    private String productName;
    /**订单号*/
    @ApiModelProperty(value = "订单号")
    private String orderNum;
    @ApiModelProperty(value = "仓库lot")
    private String warehouseLot;

}
