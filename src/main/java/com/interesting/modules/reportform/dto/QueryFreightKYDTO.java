package com.interesting.modules.reportform.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryFreightKYDTO {
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    @ApiModelProperty(value = "提单号")
    private String billOfLandingNo;

    @ApiModelProperty(value = "工厂")
    private String factoryName;

    @ApiModelProperty(value = "客户")
    private String customer;

    @ApiModelProperty(value = "目的港")
    private String portOfDestination;

}
