package com.interesting.modules.reportform.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.common.annotation.Excel;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/9/19 9:27
 * @VERSION: V1.0
 */
@Data
public class PageRemittanceReportVO {

    @ApiModelProperty("序号")
    @Excel(name = "序号", width = 10)
    private Integer serialNumber;

    @ApiModelProperty(value = "主键")
    private String id;
    /**收汇日期*/
    @Excel(name = "收汇日期", width = 12)
    @ApiModelProperty(value = "收汇日期")
    private String remittanceDate;

    @Excel(name = "订单号", width = 20)
    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @Excel(name = "币种", width = 8)
    @ApiModelProperty("币种")
    private String currency;
    @Excel(name = "客户", width = 35)
    @ApiModelProperty(value = "客户名称")
    private String customerName;
    /**发票号*/
    @Excel(name = "发票号", width = 20)
    @ApiModelProperty(value = "发票号")
    private String invoiceNum;
    /**收汇金额*/
    @ApiModelProperty(value = "收汇金额")
    @Excel(name = "收汇金额", width = 10)
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal remittanceAmount;
    /**保理金额*/
    @ApiModelProperty(value = "保理金额")
    @Excel(name = "保理金额", width = 10)
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal factoringMoney;
    /**保理利息*/
    @ApiModelProperty(value = "保理利息")
    @Excel(name = "保理利息", width = 10)
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal factoringInterest;

    @Excel(name = "质量", width = 10)
    @ApiModelProperty(value = "质量")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal quality;
    /**手续费*/
    @ApiModelProperty(value = "手续费")
    @Excel(name = "手续费", width = 10)
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal serviceCharge;

    @ApiModelProperty(value = "折扣/其他")
    @Excel(name = "折扣/其他", width = 10)
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal discount;

    @ApiModelProperty("收汇总合计")
    @Excel(name = "收汇总合计", width = 12)
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal remittanceAmountAddFactoringMoney;

    /**备注*/
    @Excel(name = "备注", width = 60)
    @ApiModelProperty(value = "备注")
    private String remarks;

}
