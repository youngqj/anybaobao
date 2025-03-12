package com.interesting.modules.overseas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class InstUptExitStorageDetailItemDTO {
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "初始来源单据号", required = true)
    //@NotBlank(message = "初始来源单据号不能为空")
    private String sourceRepNum;
    @ApiModelProperty(value = "出库单号")
    private String warehouseExitNo;

    @ApiModelProperty(value = "单据日期")
    private Date receiptDate;


    @ApiModelProperty(value = "客户名称")
    private String customer;

    @ApiModelProperty(value = "客户订单号")
    private String customerOrderNum;

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

    @ApiModelProperty(value = "仓库id", required = true)
    @NotBlank(message = "仓库id不能为空")
    private String warehouseId;

    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;


    @ApiModelProperty(value = "仓库lot", required = true)
    @NotBlank(message = "仓库lot不能为空")
    private String warehouseLot;

    @ApiModelProperty(value = "产品id", required = true)
    @NotBlank(message = "产品id不能为空")
    private String productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "包装方式")
    private String packaging;
    @ApiModelProperty(value = "包装方式单位")
    private String packagingUnitTxt;

    @ApiModelProperty(value = "包装方式单位(id)")
    private String packagingUnit;

    @ApiModelProperty(value = "出库数量", required = true)
    @NotNull(message = "出库数量不能为空")
    private Integer exitNum;

    @ApiModelProperty(value = "出库单价", required = true)
    @NotNull(message = "出库单价不能为空")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "出库金额", required = true)
    @NotNull(message = "出库金额不能为空")
    private BigDecimal exitMoney;

    /**发票号*/
    @ApiModelProperty(value = "发票号")
    private String invoiceNum;

    /**客户id*/
    @ApiModelProperty(value = "客户id")
    private String customerId;

    /**币种*/
    @ApiModelProperty(value = "币种")
    private String currency;

    @ApiModelProperty(value = "每箱毛重")
    private BigDecimal grossWeightPerBox;

    @ApiModelProperty(value = "总毛重")
    private BigDecimal grossWeightTotal;


    @ApiModelProperty("信报保险费")
    private BigDecimal creditInsurancePremium;

    @ApiModelProperty("汇率")
    private BigDecimal exchangeRate;

    @ApiModelProperty(value = "汇率转换后 信保保险费")
    private java.math.BigDecimal creditInsurancePremiumConvert;

    @ApiModelProperty(value = "投保日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date insuranceEffectiveDate;

}
