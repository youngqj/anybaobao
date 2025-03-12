package com.interesting.modules.reportform.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.common.annotation.Excel;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FreightGWReportVO {
    @Excel(name = "序号",width = 15)
    @ApiModelProperty("序号")
    private Integer serialNumber;

    @ApiModelProperty(value = "订单明细id")
    private String orderDetailId;

    @Excel(name = "开船日期",width = 12)
    @ApiModelProperty(value = "开船时间")
    private String etd;

    @Excel(name = "客户",width = 30)
    @ApiModelProperty(value = "客户名称")
    private String customerName;
    @Excel(name = "PO号",width = 15)
    @ApiModelProperty(value = "PO号")
    private String orderNum;
    @Excel(name = "中文名",width = 25)
    @ApiModelProperty(value = "货物名称")
    private String productName;
    @Excel(name = "船公司",width = 20)
    @ApiModelProperty(value = "船公司")
    private String shipCompany;
    @Excel(name = "装货港",width = 20)
    @ApiModelProperty(value = "装货港")
    private String loadingPort;
    @Excel(name = "卸货港",width = 20)
    @ApiModelProperty(value = "卸货港")
    private String destinationPort;
    @Excel(name = "提单号",width = 22)
    @ApiModelProperty(value = "提单号")
    private String billOfLading;
    @Excel(name = "箱号",width = 18)
    @ApiModelProperty(value = "箱号")
    private String containerNo;
    @Excel(name = "到港日期",width = 10)
    @ApiModelProperty(value = "到港时间")
    private String arrivePortTime;
    @Excel(name = "海运公司",width = 10)
    @ApiModelProperty(value = "国内海运公司")
    private String gnhyfAgent;
    @Excel(name = "海运费用",width = 10)
    @ApiModelProperty(value = "国内海运费用")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnhyfPrice;
    @Excel(name = "海运付款金额",width = 16)
    @ApiModelProperty(value = "国内海运付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnhyfFinanceAmount;
    @Excel(name = "海运付款日期",width = 16)
    @ApiModelProperty(value = "国内海运付款日期")
    private String gnhyfFinanceAuditTime;
    @Excel(name = "海运申请日期",width = 16)
    @ApiModelProperty(value = "国内海运申请日期")
    private String gnhyfApplicationTime;

    @Excel(name = "港杂公司",width = 10)
    @ApiModelProperty(value = "国内港杂公司")
    private String gngzfAgent;
    @Excel(name = "港杂费",width = 10)
    @ApiModelProperty(value = "国内港杂费用")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gngzfPrice;
    @Excel(name = "港杂付款金额",width = 15)
    @ApiModelProperty(value = "国内港杂付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gngzfFinanceAmount;
    @Excel(name = "港杂付款日期",width = 15)
    @ApiModelProperty(value = "国内港杂付款日期")
    private String gngzfFinanceAuditTime;
    @Excel(name = "港杂申请日期",width = 15)
    @ApiModelProperty(value = "国内港杂申请日期")
    private String gngzfApplicationTime;

    @Excel(name = "清关行",width = 10)
    @ApiModelProperty(value = "国外清关行")
    private String gwqgfAgent;
    @Excel(name = "清关行费",width = 10)
    @ApiModelProperty(value = "国外清关行费用")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gwqgfPrice;
    @Excel(name = "清关行付款金额",width = 16)
    @ApiModelProperty(value = "国外清关行付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gwqgfFinanceAmount;
    @Excel(name = "清关行付款日期",width = 16)
    @ApiModelProperty(value = "国外清关行付款日期")
    private String gwqgfFinanceAuditTime;
    @Excel(name = "清关行申请日期",width = 16)
    @ApiModelProperty(value = "国外清关行申请日期")
    private String gwqgfApplicationTime;

    @Excel(name = "卡车公司",width = 15)
    @ApiModelProperty(value = "国外卡车公司")
    private String gwkcfAgent;
    @Excel(name = "卡车费",width = 10)
    @ApiModelProperty(value = "国外卡车费")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gwkcfPrice;
    @Excel(name = "卡车付款金额",width = 15)
    @ApiModelProperty(value = "国外卡车付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gwkcfFinanceAmount;
    @Excel(name = "卡车费付款日期",width = 16)
    @ApiModelProperty(value = "国外卡车费付款时间")
    private String gwkcfFinanceAuditTime;
    @Excel(name = "卡车费申请日期",width = 16)
    @ApiModelProperty(value = "国外卡车费申请日期")
    private String gwkcfApplicationTime;

    @Excel(name = "额外费对应公司1",width = 18)
    @ApiModelProperty(value = "国外杂费对应公司1")
    private String ewfy1Agent;
    @Excel(name = "杂费1",width = 10)
    @ApiModelProperty(value = "杂费金额1")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy1Price;
    @Excel(name = "杂费1付款金额",width = 16)
    @ApiModelProperty(value = "杂费1付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy1FinanceAmount;
    @Excel(name = "杂费1付款日期",width = 16)
    @ApiModelProperty(value = "杂费1付款时间")
    private String ewfy1FinanceAuditTime;
    @Excel(name = "杂费1申请日期",width = 16)
    @ApiModelProperty(value = "杂费1申请日期")
    private String ewfy1ApplicationTime;

    @Excel(name = "额外费对应公司2",width = 18)
    @ApiModelProperty(value = "国外杂费对应公司2")
    private String ewfy2Agent;
    @Excel(name = "杂费2",width = 10)
    @ApiModelProperty(value = "杂费金额2")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy2Price;
    @Excel(name = "杂费2付款金额",width = 16)
    @ApiModelProperty(value = "杂费2付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy2FinanceAmount;
    @Excel(name = "杂费2付款日期",width = 16)
    @ApiModelProperty(value = "杂费2付款时间")
    private String ewfy2FinanceAuditTime;
    @Excel(name = "杂费2申请日期",width = 16)
    @ApiModelProperty(value = "杂费2申请日期")
    private String ewfy2ApplicationTime;


    @Excel(name = "额外费对应公司3",width = 18)
    @ApiModelProperty(value = "国外杂费对应公司3")
    private String ewfy3Agent;
    @Excel(name = "杂费3",width = 10)
    @ApiModelProperty(value = "杂费金额3")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy3Price;
    @Excel(name = "杂费3付款金额",width = 16)
    @ApiModelProperty(value = "杂费3付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy3FinanceAmount;
    @Excel(name = "杂费3付款日期",width = 16)
    @ApiModelProperty(value = "杂费3付款时间")
    private String ewfy3FinanceAuditTime;
    @Excel(name = "杂费3申请日期",width = 16)
    @ApiModelProperty(value = "杂费3申请日期")
    private String ewfy3ApplicationTime;


    @Excel(name = "额外费对应公司4",width = 18)
    @ApiModelProperty(value = "国外杂费对应公司4")
    private String ewfy4Agent;
    @Excel(name = "杂费4",width = 10)
    @ApiModelProperty(value = "杂费金额4")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy4Price;
    @Excel(name = "杂费4付款金额",width = 16)
    @ApiModelProperty(value = "杂费4付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy4FinanceAmount;
    @Excel(name = "杂费4付款日期",width = 16)
    @ApiModelProperty(value = "杂费4付款时间")
    private String ewfy4FinanceAuditTime;
    @Excel(name = "杂费4申请日期",width = 16)
    @ApiModelProperty(value = "杂费4申请日期")
    private String ewfy4ApplicationTime;


    @Excel(name = "额外费对应公司5",width = 18)
    @ApiModelProperty(value = "国外杂费对应公司5")
    private String ewfy5Agent;
    @Excel(name = "杂费5",width = 10)
    @ApiModelProperty(value = "杂费金额5")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy5Price;
    @Excel(name = "杂费5付款金额",width = 16)
    @ApiModelProperty(value = "杂费5付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy5FinanceAmount;
    @Excel(name = "杂费5付款日期",width = 16)
    @ApiModelProperty(value = "杂费5付款时间")
    private String ewfy5FinanceAuditTime;
    @Excel(name = "杂费5申请日期",width = 16)
    @ApiModelProperty(value = "杂费5申请日期")
    private String ewfy5ApplicationTime;


    @Excel(name = "额外费对应公司6",width = 18)
    @ApiModelProperty(value = "国外杂费对应公司6")
    private String ewfy6Agent;
    @Excel(name = "杂费6",width = 10)
    @ApiModelProperty(value = "杂费金额6")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy6Price;
    @Excel(name = "杂费6付款金额",width = 16)
    @ApiModelProperty(value = "杂费6付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy6FinanceAmount;
    @Excel(name = "杂费6付款日期",width = 16)
    @ApiModelProperty(value = "杂费6付款时间")
    private String ewfy6FinanceAuditTime;
    @Excel(name = "杂费6申请日期",width = 16)
    @ApiModelProperty(value = "杂费6申请日期")
    private String ewfy6ApplicationTime;




    @Excel(name = "国内额外费用3",width = 15)
    @ApiModelProperty(value = "国内额外费用3")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnewfy3Price;
    @Excel(name = "国内额外费用3付款金额",width = 22)
    @ApiModelProperty(value = "额外费用3付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnewfy3FinanceAmount;
    @Excel(name = "国内额外费用3付款日期",width = 22)
    @ApiModelProperty(value = "额外费用3付款日期")
    private String gnewfy3FinanceAuditTime;

    @Excel(name = "国内额外费用4",width = 15)
    @ApiModelProperty(value = "国内额外费用4")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnewfy4Price;
    @Excel(name = "国内额外费用4付款金额",width = 22)
    @ApiModelProperty(value = "额外费用4付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnewfy4FinanceAmount;
    @Excel(name = "国内额外费用4付款日期",width = 22)
    @ApiModelProperty(value = "额外费用4付款日期")
    private String gnewfy4FinanceAuditTime;



}
