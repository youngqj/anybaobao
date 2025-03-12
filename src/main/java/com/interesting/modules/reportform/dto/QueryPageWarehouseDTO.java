package com.interesting.modules.reportform.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryPageWarehouseDTO {
    private String column = "create_time";

    private String order = "desc";
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    private String reportFormTimeOrder;

    private String exportColumn;


    @ApiModelProperty(value = "lot号")
    private String lot;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "仓库ID")
    private String warehouseId;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "入库时间开始")
    private String createTimeStart;

    @ApiModelProperty(value = "入库时间结束")
    private String createTimeEnd;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String orderNum;

}
