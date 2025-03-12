package com.interesting.modules.overseas.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryPageExitStorageDTO {
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    @ApiModelProperty(value = "客户订单号")
    private String customerOrderNum;

    @ApiModelProperty(value = "来源订单号")
    private String orderNum;

    @ApiModelProperty(value = "单据日期开始")
    private String receiptDateBegin;

    @ApiModelProperty(value = "单据日期结束")
    private String receiptDateEnd;
}
