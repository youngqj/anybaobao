package com.interesting.modules.overseas.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryPageRelativeOrderDTO {
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    @ApiModelProperty(value = "入库完成状态(字典)", hidden = true)
    private String enterState;

    @ApiModelProperty(value = "出库完成状态(字典)", hidden = true)
    private String exitState;

    /**订单号*/
    @ApiModelProperty(value = "订单号")
    private String orderNum;

    /**主键*/
    @ApiModelProperty(value = "客户名称")
    private String customerName;

    /**产品信息*/
    @ApiModelProperty(value = "产品信息")
    private String productInfo;

}
