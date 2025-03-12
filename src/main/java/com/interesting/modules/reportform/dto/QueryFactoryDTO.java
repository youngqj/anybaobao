package com.interesting.modules.reportform.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class QueryFactoryDTO {

    private String column = "create_time";

    private String order = "desc";

    private String reportFormTimeOrder;

    @ApiModelProperty("自定义列")
    private String exportColumn;

    @ApiModelProperty(value = "装船开始日期")
    private String etdStartDate;

    @ApiModelProperty(value = "装船结束日期")
    private String etdEndDate;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "PO号")
    private String orderNum;

    @ApiModelProperty(value = "发票号")
    private String invoiceNum;

    @ApiModelProperty(value = "中文名")
    private String productName;

    @ApiModelProperty(value = "采购合同号")
    private String purchaseContractNo;

    @ApiModelProperty(value = "品管人员")
    private String qualityControlPerson;

    @ApiModelProperty(value = "原料供应商")
    private String supplierName;

    @ApiModelProperty(value = "出货方式")
    private String inspectionNote;

    @ApiModelProperty(value = "币种")
    private String currency;

    @ApiModelProperty(value = "有无付清:1-付清 0-未付清")
    private String sqye;
}
