package com.interesting.modules.reportform.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QuerySalesStatisticsDTO {
    private String column = "etd";

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

    @ApiModelProperty("订单类型")
    private String orderType;

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

    @ApiModelProperty(value = "船公司")
    private String shipCompany;

    @ApiModelProperty(value = "原料供应商")
    private String supplierName;

    @ApiModelProperty(value = "国外收货人")
    private String overseasReceiver;

    @ApiModelProperty(value = "国内发货人")
    private String domesticSender;


    @ApiModelProperty(value = "实际交货日开始")
    private String podTimeBegin;

    @ApiModelProperty(value = "实际交货日结束")
    private String podTimeEnd;

    @ApiModelProperty(value = "是否有收款 0 未收款 1 收款完成")
    private Integer izReceiveAmount;

    @ApiModelProperty(value = "中信保开始日期")
    private String zxbStartDate;

    @ApiModelProperty(value = "中信保结束日期")
    private String zxbEndDate;

    @ApiModelProperty(value = "投保开始日期 格式：2024-02-22")
    private String insuranceEffectiveDateStart;

    @ApiModelProperty(value = "投保结束日期 格式：2024-02-22")
    private String insuranceEffectiveDateEnd;


    @ApiModelProperty(value = "收汇到期开始日期")
    private String shdqStartDate;

    @ApiModelProperty(value = "收汇到期结束日期")
    private String shdqEndDate;

    @ApiModelProperty("币种")
    public String currency;


}
