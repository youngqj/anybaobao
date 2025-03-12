package com.interesting.modules.overseas.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.common.annotation.Excel;
import com.interesting.config.BigDecimalSerializer;
import com.interesting.config.BigDecimalSerializer3;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ExitStorageDetailPageVO {
    @ApiModelProperty(value = "主键")
    private String id;

    @Excel(name = "出库单号", width = 15)
    @ApiModelProperty(value = "出库单号")
    private String warehouseExitNo;

    @Excel(name = "出库单号", width = 15)
    @ApiModelProperty(value = "单据日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date receiptDate;

    @Excel(name = "客户名称", width = 20)
    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @Excel(name = "客户订单号", width = 15)
    @ApiModelProperty(value = "客户订单号")
    private String customerOrderNum;

    @Excel(name = "初始来源订单号", width = 15)
    @ApiModelProperty(value = "初始来源订单号")
    private String sourceRepNum;

    @Excel(name = "bol", width = 10)
    @ApiModelProperty(value = "bol")
    private String bol;

    @Excel(name = "bol时间", width = 15)
    @ApiModelProperty(value = "bol时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bolTime;

    @Excel(name = "pod时间", width = 15)
    @ApiModelProperty(value = "pod时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date podTime;

    @Excel(name = "货款到期日", width = 15)
    @ApiModelProperty(value = "货款到期日")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date paymentExpireDate;

    @ApiModelProperty(value = "仓库id")
    private String warehouseId;

    @Excel(name = "仓库名称", width = 20)
    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;

    @Excel(name = "仓库lot", width = 20)
    @ApiModelProperty(value = "仓库lot")
    private String warehouseLot;

    @ApiModelProperty(value = "产品id")
    private String productId;

    @Excel(name = "产品名称", width = 20)
    @ApiModelProperty(value = "产品名称")
    private String productName;

    @Excel(name = "包装方式", width = 10)
    @ApiModelProperty(value = "包装方式")
    private String packaging;

    @ApiModelProperty(value = "包装方式单位(id)")
    private String packagingUnit;

    @Excel(name = "包装方式单位", width = 10)
    @ApiModelProperty(value = "包装方式单位")
    private String packagingUnitTxt;

    @Excel(name = "出库数量", width = 10)
    @ApiModelProperty(value = "出库数量")
    private Integer exitNum;

    @Excel(name = "出库单价", width = 10)
    @ApiModelProperty(value = "出库单价")
    @JsonSerialize(using = BigDecimalSerializer3.class)
    private BigDecimal unitPrice;

    @Excel(name = "出库金额", width = 10)
    @ApiModelProperty(value = "出库金额")
    @JsonSerialize(using = BigDecimalSerializer3.class)
    private BigDecimal exitMoney;

    @Excel(name = "发票号", width = 10)
    @ApiModelProperty(value = "发票号")
    private String invoiceNum;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "特殊情况备注")
    private String specialRemark;

    @Excel(name = "信保保险费(CNY)", width = 10)
    @ApiModelProperty("信报保险费")
    private BigDecimal creditInsurancePremium;

    @ApiModelProperty("汇率")
    private BigDecimal exchangeRate;

    @Excel(name = "信保保险费(USD)", width = 10)
    @ApiModelProperty(value = "汇率转换后 信保保险费")
    private java.math.BigDecimal creditInsurancePremiumConvert;

    @Excel(name = "收汇情况", width = 10)
    @ApiModelProperty(value = "收汇情况0-未收汇，1-部分收汇，2-全额收汇")
    private Integer remmitStatus;

    @Excel(name = "投保日期", width = 10)
    @ApiModelProperty(value = "投保日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date insuranceEffectiveDate;

    @Excel(name = "保理利息", width = 10)
    @ApiModelProperty("保理利息")
    private BigDecimal factoringInterest;
}
