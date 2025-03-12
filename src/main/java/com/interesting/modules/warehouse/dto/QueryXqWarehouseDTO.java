package com.interesting.modules.warehouse.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryXqWarehouseDTO {
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    @ApiModelProperty(value = "仓库编号")
    private String serialNum;

    @ApiModelProperty(value = "仓库名称")
    private String name;

    @ApiModelProperty(value = "仓库类型（字典）")
    private String warehouseType;
}
