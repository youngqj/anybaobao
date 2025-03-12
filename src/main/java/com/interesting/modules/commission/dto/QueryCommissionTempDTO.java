package com.interesting.modules.commission.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryCommissionTempDTO {
    private Integer pageNo = 1;

    private Integer pageSize = 10;


    @ApiModelProperty(value = "模板名称")
    private String name;

}
