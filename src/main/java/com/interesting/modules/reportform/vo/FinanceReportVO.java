package com.interesting.modules.reportform.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.common.annotation.Excel;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FinanceReportVO {

    @Excel(name = "序号",width = 15)
    @ApiModelProperty("序号")
    private Integer serialNumber;

    @ApiModelProperty(value = "订单明细id")
    private String orderDetailId;

    @Excel(name = "开船日期",width = 12)
    @ApiModelProperty(value = "开船时间")
    private String etd;

    @Excel(name = "客户",width = 35)
    @ApiModelProperty(value = "客户")
    private String customerName;

    @Excel(name = "PO号",width = 15)
    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @Excel(name = "采购合同号",width = 12)
    @ApiModelProperty(value = "采购合同号")
    private String purchaseContractNo;

    @Excel(name = "发票号",width = 15)
    @ApiModelProperty(value = "发票号")
    private String invoiceNum;

    @Excel(name = "品名",width = 28)
    @ApiModelProperty(value = "品名")
    private String productNameEn;

    @Excel(name = "中文名",width = 20)
    @ApiModelProperty(value = "中文名")
    private String productName;

    @Excel(name = "供应商",width = 28)
    @ApiModelProperty(value = "供应商")
    private String supplierName;

    @Excel(name = "采购数量（KG）",width = 12)
    @ApiModelProperty(value = "采购数量KG")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal weight;

    @Excel(name = "采购单价",width = 10)
    @ApiModelProperty(value = "采购单价")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal unitPrice;

    @Excel(name = "采购币种",width = 10)
    @ApiModelProperty(value = "采购币种")
    private String currency;

    @Excel(name = "采购总金额",width = 11)
    @ApiModelProperty(value = "采购总金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal purchaseAmount;

    @Excel(name = "开票资料",width = 10)
    @ApiModelProperty(value = "开票资料")
    private String kpzl;

    @Excel(name = "进项开票日期",width = 12)
    @ApiModelProperty(value = "进项开票日期")
    private String jxkprq;

    @Excel(name = "进项发票号",width = 15)
    @ApiModelProperty(value = "进项发票号")
    private String jxfph;

    @Excel(name = "发票金额",width = 10)
    @ApiModelProperty(value = "发票金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal fpje;

    @Excel(name = "发票税额",width = 10)
    @ApiModelProperty(value = "发票税额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal fpse;

    @Excel(name = "销售数量",width = 10)
    @ApiModelProperty(value = "销售数量")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal totalWeight;

    @Excel(name = "单位",width = 5)
    @ApiModelProperty(value = "单位")
    private String salesUnit;

    @Excel(name = "销售单价",width = 10)
    @ApiModelProperty(value = "销售单价")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal salesUnitPrice;

    @Excel(name = "销售币种",width = 10)
    @ApiModelProperty(value = "销售币种")
    private String salesCurrency;

    @Excel(name = "销售金额",width = 10)
    @ApiModelProperty(value = "销售金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal salesAmount;

    @Excel(name = "出口日期",width = 10)
    @ApiModelProperty(value = "出口日期")
    private String ckrq;

    @Excel(name = "报关单号码",width = 20)
    @ApiModelProperty(value = "报关单号码")
    private String exportDeclarationNum;

    @Excel(name = "HS编码",width = 12)
    @ApiModelProperty(value = "HS编码")
    private String hsCode;

    @Excel(name = "报关金额CFR",width = 12)
    @ApiModelProperty(value = "报关金额CFR")
    private BigDecimal bgjeCfr;

    @Excel(name = "海运费",width = 10)
    @ApiModelProperty(value = "海运费")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal hyf;

    @Excel(name = "报关金额FOB",width = 12)
    @ApiModelProperty(value = "报关金额FOB")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal bgjeFob;

    @Excel(name = "开票汇率",width = 10)
    @ApiModelProperty(value = "开票汇率")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal kphl;

    @Excel(name = "出口发票金额",width = 15)
    @ApiModelProperty(value = "出口发票金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ckfpje;

    @Excel(name = "出口发票日期",width = 10)
    @ApiModelProperty(value = "出口发票日期")
    private String ckfprq;

    @Excel(name = "出口发票号码",width = 12)
    @ApiModelProperty(value = "出口发票号码")
    private String ckfphm;

    @Excel(name = "合同",width = 10)
    @ApiModelProperty(value = "合同")
    private String ht;

    @Excel(name = "退税率",width = 10)
    @ApiModelProperty(value = "退税率")
    private String tsl;

    @Excel(name = "退税办理时间",width = 10)
    @ApiModelProperty(value = "退税时间")
    private String tssj;

    @Excel(name = "退税金额",width = 10)
    @ApiModelProperty(value = "退税金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal tsje;

    @Excel(name = "收到退税时间",width = 10)
    @ApiModelProperty(value = "收到退税时间")
    private String sdtssj;

    @Excel(name = "首次收汇金额",width = 15)
    @ApiModelProperty(value = "收汇金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal shje;

    @Excel(name = "首次收汇日期",width = 15)
    @ApiModelProperty(value = "收汇时间")
    private String shsj;

    @Excel(name = "收汇银行",width = 15)
    @ApiModelProperty(value = "收汇银行")
    private String shyh;

    @Excel(name = "出货方式",width = 15)
    @ApiModelProperty(value = "出货方式")
    private String chfs;

    @Excel(name = "国外收货人",width = 28)
    @ApiModelProperty(value = "国外收货人")
    private String overseasReceiver;

    @Excel(name = "国内发货人",width = 25)
    @ApiModelProperty(value = "国内发货人")
    private String domesticSender;

    @Excel(name = "出口发票抬头",width = 25)
    @ApiModelProperty(value = "出口发票抬头")
    private String ckfptt;

    /**
     * 比例
     */
    @Excel(name = "订单个数",width = 10)
    @ApiModelProperty(value = "订单个数")
    private Integer recordCount;


    @Excel(name = "版面要求",width = 15)
    @ApiModelProperty(value = "版面要求")
    private String layoutRequirements;


    @Excel(name = "二次收汇金额",width = 15)
    @ApiModelProperty(value = "二次收汇金额")
    private BigDecimal shje2;

    @Excel(name = "二次收汇日期",width = 15)
    @ApiModelProperty(value = "二次收汇日期")
    private String shsj2;

    @Excel(name = "末次收汇金额",width = 15)
    @ApiModelProperty(value = "末次收汇金额")
    private BigDecimal shje3;

    @Excel(name = "末次收汇日期",width = 15)
    @ApiModelProperty(value = "末次收汇日期")
    private String shsj3;

}
