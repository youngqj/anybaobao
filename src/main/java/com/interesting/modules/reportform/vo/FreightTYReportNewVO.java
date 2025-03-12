package com.interesting.modules.reportform.vo;

import com.interesting.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FreightTYReportNewVO {
    @Excel(name = "序号", width = 10)
    @ApiModelProperty("序号")
    private Integer serialNumber;

    @ApiModelProperty(value = "货运id")
    private String id;

//    @Excel(name = "开船日期",width = 12)
//    @ApiModelProperty(value = "开船时间")
//    private String etd;

    @Excel(name = "PO号", width = 15)
    @ApiModelProperty(value = "PO号")
    private String orderNum;

    @Excel(name = "船公司", width = 15)
    @ApiModelProperty(value = "船公司")
    private String returnShipCompany;

    @Excel(name = "船名航次", width = 15)
    @ApiModelProperty(value = "船名航次")
    private String returnVoyageNumber;

    @Excel(name = "装运港", width = 15)
    @ApiModelProperty(value = "装运港")
    private String returnLoadingPort;

    @Excel(name = "目的港", width = 15)
    @ApiModelProperty(value = "目的港")
    private String returnDestinationPort;

    @Excel(name = "提单号码", width = 20)
    @ApiModelProperty(value = "提单号码")
    private String returnBillOfLading;

    @Excel(name = "货柜号码", width = 18)
    @ApiModelProperty(value = "货柜号码")
    private String returnContainerNo;

    @Excel(name = "净重(KGS)", width = 15)
    @ApiModelProperty(value = "退货净重(KGS）")
    private java.math.BigDecimal returnNetWeight;

    @Excel(name = "毛重(KGS)", width = 15)
    @ApiModelProperty(value = "退货毛重(KGS)")
    private java.math.BigDecimal returnGrossWeight;

    @Excel(name = "箱数", width = 10)
    @ApiModelProperty(value = "退货箱数")
    private Integer returnBoxNum;

    @Excel(name = "产品名称", width = 15)
    @ApiModelProperty(value = "产品名称")
    private String productName;

    @Excel(name = "版面要求", width = 15)
    @ApiModelProperty(value = "版面要求")
    private String layoutRequirements;

    @Excel(name = "代理商", width = 25)
    @ApiModelProperty(value = "代理商")
    private String agent;

    //    @Excel(name = "费用类型",width = 15)
    @ApiModelProperty(value = "费用类型（字典）")
    private String feeType;

    @Excel(name = "费用类型", width = 15)
    @ApiModelProperty(value = "费用类型")
    private String feeTypeName;

    @Excel(name = "申请时间", width = 15)
    @ApiModelProperty(value = "申请时间")
    private String applicationTime;

    @Excel(name = "付款时间", width = 15)
    @ApiModelProperty(value = "付款时间")
    private String payTime;


    @Excel(name = "备注", width = 50)
    @ApiModelProperty(value = "备注")
    private String remark;

    @Excel(name = "币种", width = 10)
    @ApiModelProperty(value = "币种")
    private String currency;

    @Excel(name = "价格", width = 10)
    @ApiModelProperty(value = "价格")
    private java.math.BigDecimal price;

    @Excel(name = "财务确认金额", width = 15)
    @ApiModelProperty(value = "财务确认金额")
    private java.math.BigDecimal financeAmount;

    @Excel(name = "财务审核日期", width = 15)
    @ApiModelProperty(value = "财务审核日期")
    private String financeAuditTime;

}