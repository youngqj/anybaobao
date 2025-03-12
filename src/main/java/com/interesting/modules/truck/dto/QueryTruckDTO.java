package com.interesting.modules.truck.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryTruckDTO {
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    @ApiModelProperty(value = "PO号")
    private String orderNum;

    @ApiModelProperty(value = "卡车")
    private String truck;
}
