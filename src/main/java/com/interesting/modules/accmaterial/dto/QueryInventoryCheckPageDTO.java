package com.interesting.modules.accmaterial.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryInventoryCheckPageDTO {
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    @ApiModelProperty("盘点开始时间")
    private String checkTimeStart;

    @ApiModelProperty("盘点结束时间")
    private String checkTimeEnd;

    @ApiModelProperty("盘点人id")
    private String checkUserId;
}
