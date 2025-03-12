package com.interesting.modules.reportform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.common.annotation.Excel;
import com.interesting.config.BigDecimalSerializer;
import com.interesting.config.BigDecimalSerializer3;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;

@Data
public class SalesStatisticsReportVO {

    @ApiModelProperty("序号")
    @Excel(name = "序号",width = 10)
    private Integer serialNumber;

    @ApiModelProperty(value = "订单明细id")
    private String orderDetailId;

    @Excel(name = "开船日期",width = 12)
    @ApiModelProperty(value = "开船时间")
    private String etd;

    @Excel(name = "收款到期日",width = 12)
    @ApiModelProperty(value = "收汇到期时间")
    private String remittanceTime;


    @Excel(name = "客户",width = 35)
    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @Excel(name = "订单类型",width = 10)
    @ApiModelProperty("订单类型")
    private String orderType;

    @Excel(name = "PO号",width = 15)
    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @Excel(name = "采购合同号",width = 15)
    @ApiModelProperty(value = "采购合同号")
    private String purchaseContractNo;

    @Excel(name = "发票号",width = 12)
    @ApiModelProperty(value = "发票号")
    private String invoiceNum;
    @Excel(name = "英文品名",width = 28)
    @ApiModelProperty(value = "英文品名")
    private String productNameEn;
    @Excel(name = "中文名",width = 24)
    @ApiModelProperty(value = "产品名称")
    private String productName;
    @Excel(name = "计算包装方式",width = 12)
    @ApiModelProperty(value = "计算包装方式")
    private String calPackaging;
    @Excel(name = "包装方式",width = 15)
    @ApiModelProperty(value = "包装方式")
    private String packaging;
    @Excel(name = "包装单位",width = 12)
    @ApiModelProperty(value = "包装单位")
    private String packagingUnit;
    @Excel(name = "品管人员",width = 10)
    @ApiModelProperty(value = "品管人员")
    private String qualityControlPerson;
    @Excel(name = "报检出货方式",width = 15)
    @ApiModelProperty(value = "报检出货方式")
    private String inspectionNote;
    @Excel(name = "代理商",width = 10)
    @ApiModelProperty(value = "代理商")
    private String agent;
    @Excel(name = "船公司",width = 10)
    @ApiModelProperty(value = "船公司")
    private String shipCompany;
    @Excel(name = "装运港",width = 18)
    @ApiModelProperty(value = "装货港")
    private String loadingPort;
    @Excel(name = "目的港",width = 12)
    @ApiModelProperty(value = "目的港")
    private String destinationPort;
    @Excel(name = "提单号码",width = 19)
    @ApiModelProperty(value = "提单号码")
    private String billOfLading;
    @Excel(name = "货柜号码",width = 14)
    @ApiModelProperty(value = "货柜号码")
    private String containerNo;
    @Excel(name = "装柜日期",width = 10)
    @ApiModelProperty(value = "装柜日期")
    private String loadingDate;
    @Excel(name = "ETA",width = 10)
    @ApiModelProperty(value = "ETA")
    private String eta;
    @Excel(name = "原料供应商",width = 26)
    @ApiModelProperty(value = "原料供应商")
    private String supplierName;
    @Excel(name = "国内发货人(提单)",width = 25)
    @ApiModelProperty(value = "境内发货人(提单)")
    private String domesticSender;
    @Excel(name = "国外收货人",width = 28)
    @ApiModelProperty(value = "境外收货人")
    private String overseasReceiver;

//    @ApiModelProperty(value = "销售数量")
//    private String totalWeight;
    @Excel(name = "销售数量",width = 10)
    @ApiModelProperty(value = "销售数量")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal weight;
    @Excel(name = "重量单位",width = 10)
    @ApiModelProperty(value = "重量单位")
    private String unit;
    @Excel(name = "销售单价",width = 10)
    @ApiModelProperty(value = "销售单价")
    @JsonSerialize(using = BigDecimalSerializer3.class)
    private BigDecimal unitPrice;
    @Excel(name = "币种",width = 5)
    @ApiModelProperty(value = "币种")
    private String currency;
    @Excel(name = "销售总额",width = 10)
    @ApiModelProperty(value = "销售总额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal salesAmount;
    @Excel(name = "实际交货日",width = 10)
    @ApiModelProperty(value = "实际交货日")
    private String podTime;
    @Excel(name = "应收总额",width = 15)
    @ApiModelProperty(value = "销售总额-订单扣除")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal salesAmountSuddkc;
    @Excel(name = "第三方进口商佣金",width = 15)
    @ApiModelProperty(value = "第三方进口佣金")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal thirdPartImportCommission;
    @Excel(name = "总第三方进口佣金",width =14)
    @ApiModelProperty(value = "总第三方进口佣金")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal totalThirdPartImportCommission;
    @Excel(name = "中间商佣金",width = 12)
    @ApiModelProperty(value = "中间商佣金")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal middleManCommission;
    @Excel(name = "总中间商佣金",width = 12)
    @ApiModelProperty(value = "总中间商佣金")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal totalMiddleManCommission;
    @Excel(name = "信保保险费",width = 11)
    @ApiModelProperty(value = "信保保险费")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal creditInsurancePremium;
    @Excel(name = "保理利息",width = 10)
    @ApiModelProperty(value = "保理利息")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal factoringInterest;
    @Excel(name = "质量扣款",width = 10)
    @ApiModelProperty(value = "质量扣款")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal discount;
    @Excel(name = "手续费",width = 10)
    @ApiModelProperty(value = "手续费")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal serviceCharge;
    @Excel(name = "收款金额",width = 15)
    @ApiModelProperty(value = "收款金额（一）")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal remittanceAmount1;
    @Excel(name = "收款金额",width = 15)
    @ApiModelProperty(value = "收款金额（二）")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal remittanceAmount2;
    @Excel(name = "保理金额",width = 15)
    @ApiModelProperty(value = "保理金额（一）")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal factoringMoney1;
    @Excel(name = "保理金额",width = 15)
    @ApiModelProperty(value = "保理金额（二）")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal factoringMoney2;
    @Excel(name = "实收总金额",width = 10)
    @ApiModelProperty(value = "实收总金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal realReceiveAmount;
    @Excel(name = "未收款",width = 15)
    @ApiModelProperty(value = "未收款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal unReceiveAmount;
    @Excel(name = "收汇日期",width = 37)
    @ApiModelProperty(value = "收汇日期")
    private String remittanceDate;
    @Excel(name = "备注1（销售收款）",width = 60)
    @ApiModelProperty(value = "收汇情况说明")
    private String remittanceRemark;
    @Excel(name = "备注2（异常情况）",width = 60)
    @ApiModelProperty(value = "质量问题说明")
    private String problemNote;
    @Excel(name = "订单条数",width = 10)
    @ApiModelProperty(value = "订单条数")
    private Integer recordCount;
    @Excel(name = "投保日期",width = 10)
    @ApiModelProperty(value = "投保日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.util.Date insuranceEffectiveDate;

    @Excel(name = "额外费用",width = 10)
    @ApiModelProperty(value = "额外费用")
    private BigDecimal extraFree;
    @Excel(name = "订单扣除佣金",width = 10)
    @ApiModelProperty(value = "佣金")
    private BigDecimal amount;
    @Excel(name = "折扣/其他",width = 10)
    @ApiModelProperty(value = "其他")
    private BigDecimal other;

    @ApiModelProperty(value = "排序")
    private Integer ranking;

}
