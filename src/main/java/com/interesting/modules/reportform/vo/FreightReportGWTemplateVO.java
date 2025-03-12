package com.interesting.modules.reportform.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.common.annotation.Excel;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FreightReportGWTemplateVO {


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
    private String gnhyfFinanceAuditTime;


    @ApiModelProperty(value = "国内港杂公司")
    private String gngzfAgent;

    @ApiModelProperty(value = "国内港杂费用")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gngzfPrice;

    @ApiModelProperty(value = "国内港杂付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gngzfFinanceAmount;

    @ApiModelProperty(value = "国内港杂付款日期")
    private String gngzfFinanceAuditTime;


    @ApiModelProperty(value = "国外清关公司")
    private String gwqgfAgent;

    @ApiModelProperty(value = "国外清关费")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gwqgfPrice;

    @ApiModelProperty(value = "国外清关付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gwqgfFinanceAmount;

    @ApiModelProperty(value = "国外清关付款日期")
    private String gwqgfFinanceAuditTime;


    @ApiModelProperty(value = "国外卡车公司")
    private String gwkcfAgent;

    @ApiModelProperty(value = "国外卡车费")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gwkcfPrice;

    @ApiModelProperty(value = "国外卡车付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gwkcfFinanceAmount;

    @ApiModelProperty(value = "国外卡车付款时间")
    private String gwkcfFinanceAuditTime;


    @ApiModelProperty(value = "国外杂费1公司")
    private String ewfy1Agent;

    @ApiModelProperty(value = "国外杂费1")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy1Price;

    @ApiModelProperty(value = "国外杂费1付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy1FinanceAmount;

    @ApiModelProperty(value = "国外杂费1付款时间")
    private String ewfy1FinanceAuditTime;

    @ApiModelProperty(value = "国外杂费2公司")
    private String ewfy2Agent;

    @ApiModelProperty(value = "国外杂费2")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy2Price;

    @ApiModelProperty(value = "国外杂费2付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy2FinanceAmount;

    @ApiModelProperty(value = "国外杂费2付款时间")
    private String ewfy2FinanceAuditTime;

    @ApiModelProperty(value = "国外杂费3公司")
    private String ewfy3Agent;

    @ApiModelProperty(value = "国外杂费3")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy3Price;

    @ApiModelProperty(value = "国外杂费3付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy3FinanceAmount;

    @ApiModelProperty(value = "国外杂费3付款时间")
    private String ewfy3FinanceAuditTime;

    @ApiModelProperty(value = "国外杂费4公司")
    private String ewfy4Agent;

    @ApiModelProperty(value = "国外杂费4")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy4Price;

    @ApiModelProperty(value = "国外杂费4付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy4FinanceAmount;

    @ApiModelProperty(value = "国外杂费4付款时间")
    private String ewfy4FinanceAuditTime;


    @ApiModelProperty(value = "国外杂费2公司")
    private String ewfy5Agent;

    @ApiModelProperty(value = "国外杂费5")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy5Price;

    @ApiModelProperty(value = "国外杂费5付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy5FinanceAmount;

    @ApiModelProperty(value = "国外杂费5付款时间")
    private String ewfy5FinanceAuditTime;

    @ApiModelProperty(value = "国外杂费6公司")
    private String ewfy6Agent;

    @ApiModelProperty(value = "国外杂费6")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy6Price;

    @ApiModelProperty(value = "国外杂费6付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ewfy6FinanceAmount;

    @ApiModelProperty(value = "国外杂费6付款时间")
    private String ewfy6FinanceAuditTime;



    @Excel(name = "国内额外费用3",width = 15)
    @ApiModelProperty(value = "国内额外费用3")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnewfy3Price;
    @Excel(name = "国内额外费用3付款金额",width = 20)
    @ApiModelProperty(value = "额外费用3付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnewfy3FinanceAmount;
    @Excel(name = "国内额外费用3付款日期",width = 16)
    @ApiModelProperty(value = "额外费用3付款日期")
    private String gnewfy3FinanceAuditTime;

    @Excel(name = "国内额外费用4",width = 15)
    @ApiModelProperty(value = "国内额外费用4")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnewfy4Price;
    @Excel(name = "国内额外费用4付款金额",width = 20)
    @ApiModelProperty(value = "额外费用4付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnewfy4FinanceAmount;
    @Excel(name = "国内额外费用3付款日期",width = 20)
    @ApiModelProperty(value = "额外费用3付款日期")
    private String gnewfy4FinanceAuditTime;


}
