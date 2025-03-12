package com.interesting.modules.reportform.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.common.annotation.Excel;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FreightReportGWTemplateDTO {

    @ApiModelProperty(value = "开船时间")
    private String etd;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "PO号")
    private String orderNum;

    @ApiModelProperty(value = "国内海运公司")
    private String gnhyfAgent;

    @ApiModelProperty(value = "国内海运费用")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnhyfPrice;

    @ApiModelProperty(value = "国内海运付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnhyfFinanceAmount;

    @ApiModelProperty(value = "国内海运付款日期")
    private Date gnhyfFinanceAuditTime;

    @ApiModelProperty(value = "国内海运申请日期")
    private Date gnhyfApplicationTime;


    @ApiModelProperty(value = "国内港杂公司")
    private String gngzfAgent;

    @ApiModelProperty(value = "国内港杂费用")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gngzfPrice;

    @ApiModelProperty(value = "国内港杂付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gngzfFinanceAmount;

    @ApiModelProperty(value = "国内港杂付款日期")
    private Date gngzfFinanceAuditTime;

    @ApiModelProperty(value = "国内港杂申请日期")
    private Date gngzfApplicationTime;


    @ApiModelProperty(value = "国外清关行")
    private String gwqgfAgent;

    @ApiModelProperty(value = "国外清关行费用")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gwqgfPrice;

    @ApiModelProperty(value = "国外清关行付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gwqgfFinanceAmount;

    @ApiModelProperty(value = "国外清关行付款日期")
    private Date gwqgfFinanceAuditTime;

    @ApiModelProperty(value = "国外清关行申请日期")
    private Date gwqgfApplicationTime;


    @ApiModelProperty(value = "国外卡车公司")
    private String gwkcfAgent;

    @ApiModelProperty(value = "国外卡车费")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gwkcfPrice;

    @ApiModelProperty(value = "国外卡车付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gwkcfFinanceAmount;

    @ApiModelProperty(value = "国外卡车费付款时间")
    private Date gwkcfFinanceAuditTime;

    @ApiModelProperty(value = "国外卡车费申请日期")
    private Date gwkcfApplicationTime;


    @ApiModelProperty(value = "国外杂费对应公司1")
    private String ewfy1Agent;

    @ApiModelProperty(value = "杂费金额1")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy1Price;

    @ApiModelProperty(value = "杂费1付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy1FinanceAmount;

    @ApiModelProperty(value = "杂费1付款时间")
    private Date ewfy1FinanceAuditTime;

    @ApiModelProperty(value = "杂费1申请日期")
    private Date ewfy1ApplicationTime;


    @ApiModelProperty(value = "国外杂费对应公司2")
    private String ewfy2Agent;

    @ApiModelProperty(value = "杂费金额2")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy2Price;

    @ApiModelProperty(value = "杂费2付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy2FinanceAmount;

    @ApiModelProperty(value = "杂费2付款时间")
    private Date ewfy2FinanceAuditTime;

    @ApiModelProperty(value = "杂费2申请日期")
    private Date ewfy2ApplicationTime;


    @ApiModelProperty(value = "国外杂费对应公司3")
    private String ewfy3Agent;

    @ApiModelProperty(value = "杂费金额3")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy3Price;

    @ApiModelProperty(value = "杂费3付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy3FinanceAmount;

    @ApiModelProperty(value = "杂费3付款时间")
    private Date ewfy3FinanceAuditTime;

    @ApiModelProperty(value = "杂费3申请日期")
    private Date ewfy3ApplicationTime;


    @ApiModelProperty(value = "国外杂费对应公司4")
    private String ewfy4Agent;

    @ApiModelProperty(value = "杂费金额4")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy4Price;

    @ApiModelProperty(value = "杂费4付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy4FinanceAmount;

    @ApiModelProperty(value = "杂费4付款时间")
    private Date ewfy4FinanceAuditTime;

    @ApiModelProperty(value = "杂费4申请日期")
    private Date ewfy4ApplicationTime;


    @ApiModelProperty(value = "国外杂费对应公司5")
    private String ewfy5Agent;

    @ApiModelProperty(value = "杂费金额5")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy5Price;

    @ApiModelProperty(value = "杂费5付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy5FinanceAmount;

    @ApiModelProperty(value = "杂费5付款时间")
    private Date ewfy5FinanceAuditTime;

    @ApiModelProperty(value = "杂费5申请日期")
    private Date ewfy5ApplicationTime;


    @ApiModelProperty(value = "国外杂费对应公司6")
    private String ewfy6Agent;

    @ApiModelProperty(value = "杂费金额6")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy6Price;

    @ApiModelProperty(value = "杂费6付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy6FinanceAmount;

    @ApiModelProperty(value = "杂费6付款时间")
    private Date ewfy6FinanceAuditTime;

    @ApiModelProperty(value = "杂费6申请日期")
    private Date ewfy6ApplicationTime;


    @Excel(name = "国内额外费用3",width = 15)
    @ApiModelProperty(value = "国内额外费用3")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnewfy3Price;
    @Excel(name = "额外费用3付款金额",width = 20)
    @ApiModelProperty(value = "额外费用3付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnewfy3FinanceAmount;
    @Excel(name = "额外费用3付款日期",width = 16)
    @ApiModelProperty(value = "额外费用3付款日期")
    private Date gnewfy3FinanceAuditTime;

    @Excel(name = "国内额外费用4",width = 15)
    @ApiModelProperty(value = "国内额外费用4")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnewfy4Price;
    @Excel(name = "额外费用4付款金额",width = 20)
    @ApiModelProperty(value = "额外费用4付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnewfy4FinanceAmount;
    @Excel(name = "额外费用3付款日期",width = 20)
    @ApiModelProperty(value = "额外费用3付款日期")
    private Date gnewfy4FinanceAuditTime;
}
