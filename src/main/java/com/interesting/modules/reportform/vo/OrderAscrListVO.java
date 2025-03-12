package com.interesting.modules.reportform.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.common.annotation.Excel;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderAscrListVO {

    @Excel(name = "序号",width = 10)
    @ApiModelProperty("序号")
    private Integer serialNumber;

    @Excel(name = "付款状态",width = 10)
    @ApiModelProperty(value = "付款状态 1-未付款 2-已付款")
    private Integer financeStatus;

    @Excel(name = "辅料供应商",width = 15)
    @ApiModelProperty(value = "辅料供应商")
    private String supplierName;

    @Excel(name = "原料供应商",width = 26)
    @ApiModelProperty("原料供应商")
    private String originalSupplierName;

    @Excel(name = "采购合同号",width = 15)
    @ApiModelProperty(value = "采购合同号")
    private String purchaseContractNo;

    @Excel(name = "订单号",width = 13)
    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @Excel(name = "辅料名称",width = 20)
    @ApiModelProperty(value = "辅料名称")
    private String accessoryName;

    @Excel(name = "分类名称",width = 15)
    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @Excel(name = "尺寸",width = 16)
    @ApiModelProperty(value = "尺寸")
    private String size;

    @Excel(name = "材质规格",width = 16)
    @ApiModelProperty(value = "材质规格")
    private String materialSpecification;

    @Excel(name = "订单数量",width = 10)
    @ApiModelProperty(value = "订单数量")
    private Integer orderQuantity;

    @Excel(name = "币种",width = 10)
    @ApiModelProperty("币种")
    private String currency;

    @Excel(name = "采购单价",width = 10)
    @ApiModelProperty(value = "采购单价")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal unitPrice;

    @Excel(name = "采购金额",width = 10)
    @ApiModelProperty(value = "采购金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal purchaseAmount;

    @Excel(name = "税率",width = 10)
    @ApiModelProperty(value = "税率")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal taxRate;

    @Excel(name = "含税金额",width = 10)
    @ApiModelProperty(value = "含税金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal taxIncludedAmount;

    @Excel(name = "付款金额",width = 10)
    @ApiModelProperty(value = "付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal financeAuditAmount;

    @Excel(name = "未付款金额",width = 12)
    @ApiModelProperty(value = "未付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal unFinanceAuditAmount;







}
