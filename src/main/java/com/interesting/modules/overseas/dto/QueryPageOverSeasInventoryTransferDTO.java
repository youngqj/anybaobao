package com.interesting.modules.overseas.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryPageOverSeasInventoryTransferDTO {
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    @ApiModelProperty("调拨开始时间")
    private String transferTimeStart;

    @ApiModelProperty("调拨结束时间")
    private String transferTimeEnd;

    @ApiModelProperty("调拨人id")
    private String transferUserId;

    @ApiModelProperty("调出仓id")
    private String sourceWarehouseId;

    @ApiModelProperty("调入仓id")
    private String transferWarehouseUserId;

    @ApiModelProperty("库存lot")
    private String warehouseLot;
}
