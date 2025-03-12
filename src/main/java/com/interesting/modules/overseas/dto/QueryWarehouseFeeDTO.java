package com.interesting.modules.overseas.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class QueryWarehouseFeeDTO {
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    @NotBlank(message = "仓库id不能为空")
    private String warehouseId;

    @ApiModelProperty(value = "年份")
    private Integer year;
}
