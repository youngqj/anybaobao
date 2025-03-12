package com.interesting.modules.reportform.vo;

import com.interesting.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FreightTYReportVO {
    @Excel(name = "序号",width = 10)
    @ApiModelProperty("序号")
    private Integer serialNumber;

    @ApiModelProperty(value = "订单明细id")
    private String orderDetailId;

    @Excel(name = "开船日期",width = 12)
    @ApiModelProperty(value = "开船时间")
    private String etd;

    @Excel(name = "客户",width = 32)
    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @Excel(name = "PO号",width = 12)
    @ApiModelProperty(value = "PO号")
    private String orderNum;

    @Excel(name = "中文名",width = 15)
    @ApiModelProperty(value = "货物名称")
    private String productName;

    @Excel(name = "船公司",width = 10)
    @ApiModelProperty(value = "船公司")
    private String shipCompany;

    @Excel(name = "装货港",width = 10)
    @ApiModelProperty(value = "装货港")
    private String loadingPort;

    @Excel(name = "卸货港",width = 12)
    @ApiModelProperty(value = "卸货港")
    private String destinationPort;

    @Excel(name = "提单号",width = 20)
    @ApiModelProperty(value = "提单号")
    private String billOfLading;

    @Excel(name = "箱号",width = 18)
    @ApiModelProperty(value = "箱号")
    private String containerNo;

    @Excel(name = "到港日期",width = 10)
    @ApiModelProperty(value = "到港时间")
    private String arrivePortTime;

    @Excel(name = "国内海运公司",width = 15)
    @ApiModelProperty(value = "国内海运公司")
    private String gnhyfAgent;

    @Excel(name = "国内海运费用",width = 15)
    @ApiModelProperty(value = "国内海运费用")
    private BigDecimal gnhyfPrice;

    @Excel(name = "国内海运付款金额",width = 18)
    @ApiModelProperty(value = "国内海运付款金额")
    private BigDecimal gnhyfFinanceAmount;

    @Excel(name = "国内海运付款日期",width = 18)
    @ApiModelProperty(value = "国内海运付款日期")
    private String gnhyfFinanceAuditTime;

    @Excel(name = "国内海运申请日期",width = 18)
    @ApiModelProperty(value = "国内海运申请日期")
    private String gnhyfApplicationTime;

    @Excel(name = "国内港杂公司",width = 15)
    @ApiModelProperty(value = "国内港杂公司")
    private String gngzfAgent;

    @Excel(name = "国内港杂费用",width = 15)
    @ApiModelProperty(value = "国内港杂费用")
    private BigDecimal gngzfPrice;

    @Excel(name = "国内港杂付款金额",width = 18)
    @ApiModelProperty(value = "国内港杂付款金额")
    private BigDecimal gngzfFinanceAmount;

    @Excel(name = "国内港杂申请日期",width = 18)
    @ApiModelProperty(value = "国内港杂申请日期")
    private String gngzfApplicationTime;

    @Excel(name = "国内港杂付款日期",width = 18)
    @ApiModelProperty(value = "国内港杂付款日期")
    private String gngzfFinanceAuditTime;

    @Excel(name = "国外卡车公司",width = 15)
    @ApiModelProperty(value = "国外卡车公司")
    private String gwkcfAgent;

    @Excel(name = "国外卡车费用",width = 12)
    @ApiModelProperty(value = "国外卡车费")
    private String gwkcfPrice;

    @Excel(name = "国外卡车付款金额",width = 18)
    @ApiModelProperty(value = "国外卡车付款金额")
    private BigDecimal gwkcfFinanceAmount;

    @Excel(name = "国外卡车费申请日期",width = 18)
    @ApiModelProperty(value = "国外卡车申请日期")
    private String gwkcfApplicationTime;

    @Excel(name = "国外卡车费付款日期",width = 18)
    @ApiModelProperty(value = "国外卡车费付款时间")
    private String gwkcfFinanceAuditTime;

    @Excel(name = "国外额外费用对应公司1",width = 24)
    @ApiModelProperty(value = "国外额外费用1对应公司")
    private String ewfy1Agent;

    @Excel(name = "国外额外费用1",width = 18)
    @ApiModelProperty(value = "国外额外费用1")
    private BigDecimal ewfy1Price;

    @Excel(name = "国外额外费用1付款金额",width = 24)
    @ApiModelProperty(value = "国外额外费用1付款金额")
    private BigDecimal ewfy1FinanceAmount;

    @Excel(name = "国外额外费用1申请日期",width = 24)
    @ApiModelProperty(value = "国外额外费用1申请日期")
    private String ewfy1ApplicationTime;

    @Excel(name = "国外额外费用1付款日期",width = 24)
    @ApiModelProperty(value = "国外额外费用1付款日期")
    private String ewfy1FinanceAuditTime;

}
