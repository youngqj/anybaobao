package com.interesting.modules.reportform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.common.annotation.Excel;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FreightReportTemplateVO {


    @ApiModelProperty(value = "开船时间")
    private String etd;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "PO号")
    private String orderNum;

    @ApiModelProperty(value = "国内卡车公司")
    private String gnlyfAgent;

    @ApiModelProperty(value = "国内卡车费用RMB")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnlyfPrice;

    @ApiModelProperty(value = "国内卡车付款金额RMB")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnlyfFinanceAmount;

    @ApiModelProperty(value = "国内卡车申请日期")

    private String gnlyfApplicationTime;

    @ApiModelProperty(value = "国内卡车付款日期")

    private String gnlyfFinanceAuditTime;


    @ApiModelProperty(value = "国内报关公司")
    private String gnbgfAgent;

    @ApiModelProperty(value = "国内报关费用")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnbgfPrice;

    @ApiModelProperty(value = "国内报关付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnbgfFinanceAmount;

    @ApiModelProperty(value = "国内报关申请日期")

    private String gnbgfApplicationTime;

    @ApiModelProperty(value = "国内报关付款日期")


    private String gnbgfFinanceAuditTime;


    @ApiModelProperty(value = "海运保险公司")
    private String gnhyfAgent;

    @ApiModelProperty(value = "海运保险USD")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnhyfPrice;

    @ApiModelProperty(value = "海运付款金额USD")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnhyfFinanceAmount;

    @ApiModelProperty(value = "国内海运申请日期")


    private String gnhyfApplicationTime;

    @ApiModelProperty(value = "海运保险付款日期")


    private String gnhyfFinanceAuditTime;


    @ApiModelProperty(value = "舱单系统公司")
    private String gncdfAgent;

    @ApiModelProperty(value = "舱单金额RMB")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gncdfPrice;

    @ApiModelProperty(value = "舱单付款金额RMB")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gncdfFinanceAmount;

    @ApiModelProperty(value = "舱单保险申请日期")

    private String gncdfApplicationTime;

    @ApiModelProperty(value = "舱单保险付款时间")

    private String gncdfFinanceAuditTime;


    @ApiModelProperty(value = "送货费公司")
    private String shfAgent;

    @ApiModelProperty(value = "送货费")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal shfPrice;

    @ApiModelProperty(value = "送货费付款金额RMB")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal shfFinanceAmount;

    @ApiModelProperty(value = "送货费申请日期")

    private String shfApplicationTime;

    @ApiModelProperty(value = "送货费付款时间")

    private String shfFinanceAuditTime;

    @ApiModelProperty(value = "送货费应付金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal shfPayableAmount;

    @ApiModelProperty(value = "送货费已付金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal shfPaidAmount;

    @Excel(name = "国内额外费用1",width = 15)
    @ApiModelProperty(value = "国内额外费用1")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnewfy1Price;
    @Excel(name = "国内额外费用1付款金额",width = 20)
    @ApiModelProperty(value = "额外费用1付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnewfy1FinanceAmount;
    @Excel(name = "国内额外费用1付款日期",width = 16)
    @ApiModelProperty(value = "额外费用1付款日期")
    private String gnewfy1FinanceAuditTime;

    @Excel(name = "国内额外费用2",width = 15)
    @ApiModelProperty(value = "国内额外费用2")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnewfy2Price;
    @Excel(name = "国内额外费用2付款金额",width = 20)
    @ApiModelProperty(value = "额外费用2付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnewfy2FinanceAmount;
    @Excel(name = "国内额外费用2付款日期",width = 20)
    @ApiModelProperty(value = "额外费用2付款日期")
    private String gnewfy2FinanceAuditTime;
}
