package com.interesting.modules.reportform.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EditFreightDTO {
    @ApiModelProperty(value = "PO号")
    private String orderNum;


    @ApiModelProperty(value = "国内卡车付款金额RMB")
    private BigDecimal gnlyfFinanceAmount;

    @ApiModelProperty(value = "国内卡车付款日期")
    private Date gnlyfFinanceAuditTime;


    @ApiModelProperty(value = "国内报关付款金额")
    private BigDecimal gnbgfFinanceAmount;

    @ApiModelProperty(value = "国内报关付款日期")
    private Date gnbgfFinanceAuditTime;


    @ApiModelProperty(value = "付款金额USD")
    private BigDecimal gnhyfFinanceAmount;

    @ApiModelProperty(value = "海运保险付款日期")
    private Date gnhyfFinanceAuditTime;

    @ApiModelProperty(value = "舱单付款金额RMB")
    private BigDecimal gncdfFinanceAmount;

    @ApiModelProperty(value = "舱单保险付款时间")
    private Date gncdfFinanceAuditTime;
}
