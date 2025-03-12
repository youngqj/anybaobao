package com.interesting.modules.overseas.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QueryPageEnterStorageDetailDTO {
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    @ApiModelProperty(value = "单据日期开始")
    private String receiptDateBegin;

    @ApiModelProperty(value = "单据日期结束")
    private String receiptDateEnd;

    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "仓库lot")
    private String warehouseLot;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "初始来源订单号")
    private String orderNum;

    @ApiModelProperty(value = "入库单号")
    private String warehouseEnterNo;
}
