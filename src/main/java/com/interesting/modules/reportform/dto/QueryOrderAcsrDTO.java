package com.interesting.modules.reportform.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryOrderAcsrDTO {

    private Integer pageNo = 1;
    private Integer pageSize = 10;

    @ApiModelProperty(value = "辅料供应商名称")
    private String supplierName;

    private String exportColumn;

    @ApiModelProperty("原料供应商")
    private String originalSupplierName;

    @ApiModelProperty(value = "辅料名称")
    private String accessoryName;

    @ApiModelProperty(value = "采购合同号")
    private String purchaseContractNo;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @ApiModelProperty(value = "付款状态 0未付款 1-已付款")
    private Integer financeStatus;


}
