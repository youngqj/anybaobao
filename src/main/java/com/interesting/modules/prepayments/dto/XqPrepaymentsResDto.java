package com.interesting.modules.prepayments.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class XqPrepaymentsResDto {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "收款工厂id")
    private String supplierId;

    @ApiModelProperty(value = "收款工厂名称")
    private String supplierName;

    @ApiModelProperty(value = "预付金额")
    private BigDecimal prepaymentAmount;

    @ApiModelProperty(value = "已付金额")
    private BigDecimal paidAmount;

    @ApiModelProperty(value = "剩余金额")
    private BigDecimal remainderAmount;

    @ApiModelProperty(value = "币种")
    private String currency;

    @ApiModelProperty(value = "付款公司id")
    private String paymentCompanyId;

    @ApiModelProperty(value = "付款公司名称")
    private String paymentCompanyName;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "付款日期")
    private Date paymentDate;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否可以编辑或删除")
    private Boolean isCanEditorDelete;
}
