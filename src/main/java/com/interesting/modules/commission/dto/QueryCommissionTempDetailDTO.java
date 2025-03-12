package com.interesting.modules.commission.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryCommissionTempDetailDTO {
    private Integer pageNo = 1;

    private Integer pageSize = 10;

    @ApiModelProperty(value = "佣金公司名称")
    private String companyName;
}
