package com.interesting.modules.overseas.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ExitStorageDetailItemVO {
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "客户订单号")
    private String customerOrderNum;

    @ApiModelProperty(value = "初始来源订单号")
    private String sourceRepNum;

    @ApiModelProperty(value = "bol")
    private String bol;

    @ApiModelProperty(value = "bol时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bolTime;

    @ApiModelProperty(value = "pod时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date podTime;

    @ApiModelProperty(value = "货款到期日")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date paymentExpireDate;

    @ApiModelProperty(value = "配送方式")
    private String deliverMethod;

    @ApiModelProperty(value = "特殊情况备注")
    private String specialRemark;

    @ApiModelProperty(value = "来源订单id")
    private String orderDetailId;

    @ApiModelProperty(value = "仓库id")
    private String warehouseId;

    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "仓库lot")
    private String warehouseLot;

    @ApiModelProperty(value = "产品id")
    private String productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "包装方式")
    private String packaging;

    @ApiModelProperty(value = "包装方式单位(id)")
    private String packagingUnit;

    @ApiModelProperty(value = "包装方式单位")
    private String packagingUnitTxt;

    @ApiModelProperty(value = "出库数量")
    private Integer exitNum;

    @ApiModelProperty(value = "出库单价")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "出库金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal exitMoney;

    /**发票号*/
    @ApiModelProperty(value = "发票号")
    private String invoiceNum;

    /**币种*/
    @ApiModelProperty(value = "币种")
    private String currency;

    /**客户id*/
    @ApiModelProperty(value = "客户id")
    private String customerId;
}
