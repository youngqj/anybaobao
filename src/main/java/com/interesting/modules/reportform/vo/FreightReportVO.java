package com.interesting.modules.reportform.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.common.annotation.Excel;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FreightReportVO {

    @Excel(name = "序号",width = 10)
    @ApiModelProperty("序号")
    private Integer serialNumber;

    @ApiModelProperty(value = "订单明细id")
    private String orderDetailId;

    @Excel(name = "开船日期",width = 12)
    @ApiModelProperty(value = "开船时间")
    private String etd;

    @Excel(name = "客户",width = 32)
    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @Excel(name = "PO",width = 15)
    @ApiModelProperty(value = "PO号")
    private String orderNum;

    @Excel(name = "中文名",width = 28)
    @ApiModelProperty(value = "货物名称")
    private String productName;

    @Excel(name = "船公司",width = 15)
    @ApiModelProperty(value = "船公司")
    private String shipCompany;

    @Excel(name = "装货港",width = 15)
    @ApiModelProperty(value = "装货港")
    private String loadingPort;
    @Excel(name = "卸货港",width = 15)
    @ApiModelProperty(value = "卸货港")
    private String destinationPort;
    @Excel(name = "提单号",width = 20)
    @ApiModelProperty(value = "提单号")
    private String billOfLading;
    @Excel(name = "箱号",width = 15)
    @ApiModelProperty(value = "箱号")
    private String containerNo;
    @Excel(name = "到港日期",width = 20)
    @ApiModelProperty(value = "到港时间")
    private String arrivePortTime;
    @Excel(name = "陆运费公司",width = 20)
    @ApiModelProperty(value = "国内陆运费公司")
    private String gnlyfAgent;
    @Excel(name = "陆运费",width = 20)
    @ApiModelProperty(value = "国内陆运费RMB")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnlyfPrice;
    @Excel(name = "陆运费付款金额",width = 20)
    @ApiModelProperty(value = "国内陆运费付款金额RMB")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnlyfFinanceAmount;
    @Excel(name = "陆运费申请日期",width = 16)
    @ApiModelProperty(value = "国内陆运费申请日期")
    private String gnlyfApplicationTime;
    @Excel(name = "陆运费付款日期",width = 16)
    @ApiModelProperty(value = "国内陆运费付款日期")
    private String gnlyfFinanceAuditTime;
    @Excel(name = "陆运费应付金额",width = 16)
    @ApiModelProperty(value = "国内陆运费应付金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnlyfPayableAmount;
    @Excel(name = "陆运费已付金额",width = 16)
    @ApiModelProperty(value = "国内陆运费已付金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnlyfPaidAmount;
    @Excel(name = "报关费公司",width = 15)
    @ApiModelProperty(value = "国内报关费公司")
    private String gnbgfAgent;
    @Excel(name = "报关费",width = 12)
    @ApiModelProperty(value = "国内报关费费用")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnbgfPrice;
    @Excel(name = "报关费付款金额",width = 16)
    @ApiModelProperty(value = "国内报关费付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnbgfFinanceAmount;
    @Excel(name = "报关费申请日期",width = 16)
    @ApiModelProperty(value = "国内报关费申请日期")
    private String gnbgfApplicationTime;
    @Excel(name = "报关费付款日期",width = 16)
    @ApiModelProperty(value = "国内报关费付款日期")
    private String gnbgfFinanceAuditTime;
    @Excel(name = "报关费应付金额",width = 16)
    @ApiModelProperty(value = "国内报关费应付金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnbgfPayableAmount;
    @Excel(name = "报关费已付金额",width = 16)
    @ApiModelProperty(value = "国内报关费已付金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnbgfPaidAmount;

    @Excel(name = "海运保险费公司",width = 16)
    @ApiModelProperty(value = "海运保险费公司")
    private String gnhyfAgent;
    @Excel(name = "海运保险费",width = 12)
    @ApiModelProperty(value = "海运保险费")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnhyfPrice;
    @Excel(name = "海运保险费付款金额",width = 20)
    @ApiModelProperty(value = "海运保险费付款金额USD")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnhyfFinanceAmount;
    @Excel(name = "海运保险费申请日期",width = 20)
    @ApiModelProperty(value = "海运保险费申请日期")
    private String gnhyfApplicationTime;
    @Excel(name = "海运保险费付款日期",width = 20)
    @ApiModelProperty(value = "海运保险费付款日期")
    private String gnhyfFinanceAuditTime;
    @Excel(name = "海运保险费应付金额",width = 20)
    @ApiModelProperty(value = "海运保险费应付金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnhyfPayableAmount;
    @Excel(name = "海运保险费已付金额",width = 20)
    @ApiModelProperty(value = "海运保险费已付金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gnhyfPaidAmount;
    @Excel(name = "舱单费公司",width = 16)
    @ApiModelProperty(value = "国内舱单费公司")
    private String gncdfAgent;
    @Excel(name = "舱单费",width = 10)
    @ApiModelProperty(value = "国内舱单费RMB")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gncdfPrice;
    @Excel(name = "舱单费付款金额",width = 16)
    @ApiModelProperty(value = "国内舱单费付款金额RMB")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gncdfFinanceAmount;
    @Excel(name = "舱单费申请日期",width = 16)
    @ApiModelProperty(value = "国内舱单费申请日期")
    private String gncdfApplicationTime;
    @Excel(name = "舱单费付款日期",width = 16)
    @ApiModelProperty(value = "国内舱单费付款时间")
    private String gncdfFinanceAuditTime;
    @Excel(name = "舱单费应付金额",width = 16)
    @ApiModelProperty(value = "国内舱单费应付金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gncdfPayableAmount;
    @Excel(name = "舱单费已付金额",width = 16)
    @ApiModelProperty(value = "国内舱单费已付金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gncdfPaidAmount;

    @Excel(name = "送货费公司",width = 15)
    @ApiModelProperty(value = "送货费公司")
    private String shfAgent;
    @Excel(name = "送货费",width = 10)
    @ApiModelProperty(value = "送货费")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal shfPrice;
    @Excel(name = "送货费付款金额",width = 16)
    @ApiModelProperty(value = "送货费付款金额RMB")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal shfFinanceAmount;
    @Excel(name = "送货费申请日期",width = 16)
    @ApiModelProperty(value = "送货费申请日期")
    private String shfApplicationTime;
    @Excel(name = "送货费付款日期",width = 16)
    @ApiModelProperty(value = "送货费付款时间")
    private String shfFinanceAuditTime;

    @Excel(name = "送货费应付金额",width = 16)
    @ApiModelProperty(value = "送货费应付金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal shfPayableAmount;
    @Excel(name = "送货费已付金额",width = 16)
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
