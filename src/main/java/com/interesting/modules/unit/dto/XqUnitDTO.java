package com.interesting.modules.unit.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class XqUnitDTO {
    private Integer pageNo = 1;

    private Integer pageSize = 10;

    @ApiModelProperty(value = "单位名称")
    private String unitName;
}
