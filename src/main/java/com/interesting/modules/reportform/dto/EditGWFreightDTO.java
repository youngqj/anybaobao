package com.interesting.modules.reportform.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EditGWFreightDTO {
    @ApiModelProperty(value = "PO号")
    private String orderNum;


    @ApiModelProperty(value = "国内港杂付款金额RMB")
    private BigDecimal gngzfFinanceAmount;

    @ApiModelProperty(value = "国内港杂费付款日期")
    private Date gngzfFinanceAuditTime;


    @ApiModelProperty(value = "国内海运费付款金额")
    private BigDecimal gnhyfFinanceAmount;

    @ApiModelProperty(value = "国内海运费付款日期")
    private Date gnhyfFinanceAuditTime;


    @ApiModelProperty(value = "国外清关费付款金额USD")
    private BigDecimal gwqgfFinanceAmount;

    @ApiModelProperty(value = "国外清关费付款日期")
    private Date gwqgfFinanceAuditTime;

    @ApiModelProperty(value = "国外卡车付款金额RMB")
    private BigDecimal gwkcfFinanceAmount;

    @ApiModelProperty(value = "国外卡车付款时间")
    private Date gwkcfFinanceAuditTime;

    @ApiModelProperty(value = "额外费用1付款金额RMB")
    private BigDecimal ewfy1FinanceAmount;

    @ApiModelProperty(value = "额外费用1付款时间")
    private Date ewfy1FinanceAuditTime;

    @ApiModelProperty(value = "额外费用2付款金额RMB")
    private BigDecimal ewfy2FinanceAmount;

    @ApiModelProperty(value = "额外费用2付款时间")
    private Date ewfy2FinanceAuditTime;

    @ApiModelProperty(value = "额外费用3付款金额RMB")
    private BigDecimal ewfy3FinanceAmount;

    @ApiModelProperty(value = "额外费用3付款时间")
    private Date ewfy3FinanceAuditTime;

    @ApiModelProperty(value = "额外费用4付款金额RMB")
    private BigDecimal ewfy4FinanceAmount;

    @ApiModelProperty(value = "额外费用4付款时间")
    private Date ewfy4FinanceAuditTime;

    @ApiModelProperty(value = "额外费用5付款金额RMB")
    private BigDecimal ewfy5FinanceAmount;

    @ApiModelProperty(value = "额外费用5付款时间")
    private Date ewfy5FinanceAuditTime;

    @ApiModelProperty(value = "额外费用6付款金额RMB")
    private BigDecimal ewfy6FinanceAmount;

    @ApiModelProperty(value = "额外费用6付款时间")
    private Date ewfy6FinanceAuditTime;

}
