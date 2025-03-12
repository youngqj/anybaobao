package com.interesting.modules.reportform.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.common.annotation.Excel;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FactoryReportVO {
    @Excel(name = "序号",width = 10)
    @ApiModelProperty("序号")
    private Integer serialNumber;

    @ApiModelProperty(value = "订单明细id")
    private String orderDetailId;

    @Excel(name = "开船日期",width = 12)
    @ApiModelProperty(value = "开船时间")
    private String etd;

    @Excel(name = "客户",width = 35)
    @ApiModelProperty(value = "客户")
    private String customerName;

    @Excel(name = "PO号",width = 10)
    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @Excel(name = "采购合同号",width = 15)
    @ApiModelProperty(value = "采购合同号")
    private String purchaseContractNo;

    @Excel(name = "发票号",width = 12)
    @ApiModelProperty(value = "发票号")
    private String invoiceNum;

    @Excel(name = "品名",width = 25)
    @ApiModelProperty(value = "品名")
    private String productNameEn;

    @Excel(name = "中文名",width = 15)
    @ApiModelProperty(value = "中文名")
    private String productName;

    @Excel(name = "产品等级",width = 5)
    @ApiModelProperty(value = "等级")
    private String productGrade;

    @Excel(name = "包装规格",width = 24)
    @ApiModelProperty(value = "包装规格")
    private String packaging;

    @Excel(name = "品管",width = 10)
    @ApiModelProperty(value = "品管")
    private String qualityControlPerson;

    @Excel(name = "出货方式",width = 15)
    @ApiModelProperty(value = "出货方式")
    private String inspectionNote;

    @Excel(name = "装柜日期",width = 10)
    @ApiModelProperty(value = "装柜日期")
    private String loadingDate;

    @Excel(name = "到港日期",width = 10)
    @ApiModelProperty(value = "到港日期")
    private String arrivePortTime;

    @Excel(name = "采购供应商",width = 27)
    @ApiModelProperty(value = "采购供应商名称")
    private String supplierName;

    @Excel(name = "数量",width = 10)
    @ApiModelProperty(value = "数量")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal weight;

    @Excel(name = "单位",width = 5)
    @ApiModelProperty(value = "单位")
    private String weightUnit;

    @Excel(name = "采购价格",width = 10)
    @ApiModelProperty(value = "采购价格")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal unitPrice;

    @Excel(name = "实际采购价格",width = 14)
    @ApiModelProperty(value = "实际采购价格")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal realUnitPrice;

    @Excel(name = "币种",width = 5)
    @ApiModelProperty(value = "币种")
    private String currency;

    @Excel(name = "采购总金额",width = 11)
    @ApiModelProperty(value = "采购总金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal purchaseAmount;

    @Excel(name = "首付款比例",width = 11)
    @ApiModelProperty(value = "首付款比例")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal firstPaymentRate;

    @Excel(name = "首付款金额",width = 11)
    @ApiModelProperty(value = "首付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal firstPayment;

    @Excel(name = "内袋",width = 10)
    @ApiModelProperty(value = "内袋")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal ndPurchaseAmount;

    @Excel(name = "纸箱",width = 10)
    @ApiModelProperty(value = "纸箱")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal zxPurchaseAmount;

    @Excel(name = "其他辅料",width = 10)
    @ApiModelProperty(value = "其他辅料")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal qtPurchaseAmount;

    @Excel(name = "运费",width = 10)
    @ApiModelProperty(value = "运费")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal yfPurchaseAmount;

    @Excel(name = "塑料桶",width = 10)
    @ApiModelProperty(value = "塑料桶")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal sltPurchaseAmount;

    @Excel(name = "采购备注",width = 30)
    @ApiModelProperty(value = "采购备注")
    private String purchaseNote;

    @Excel(name = "财务备注",width = 30)
    @ApiModelProperty(value = "财务备注")
    private String financialNote;

    @Excel(name = "付款备注",width = 30)
    @ApiModelProperty(value = "付款备注")
    private String payRemark;

    @Excel(name = "首付款到期日期",width = 12)
    @ApiModelProperty(value = "首付款到期日")
    private String firstDqDate;

    @Excel(name = "尾付款到期日期",width = 12)
    @ApiModelProperty(value = "尾付款到期日期")
    private String endDqDate;

    @Excel(name = "工厂扣款额",width = 10)
    @ApiModelProperty(value = "工厂扣款额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal gckke;

    @Excel(name = "首付金额(一)",width = 10)
    @ApiModelProperty(value = "首付金额(一)")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal sfje;

    @Excel(name = "再付金额(二)",width = 10)
    @ApiModelProperty(value = "再付金额(二)")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal zfje;

    @Excel(name = "末付金额(三)",width = 10)
    @ApiModelProperty(value = "末付金额(三)")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal wfje;

    @Excel(name = "实付金额",width = 10)
    @ApiModelProperty(value = "实付金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal paymentAmount;

    @Excel(name = "尚欠余额",width = 10)
    @ApiModelProperty(value = "尚欠余额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal sqye;

    @Excel(name = "实付日期",width = 10)
    @ApiModelProperty(value = "实付日期")
    private String payTime;

    @Excel(name = "备注-1（销售收款）",width = 60)
    @ApiModelProperty(value = "备注-1(销售收款)")
    private String salesRemark;

    @Excel(name = "备注-2（异常情况）",width = 60)
    @ApiModelProperty(value = "备注-2(异常情况（销售加货运）)")
    private String exceptionRemark;
    @Excel(name = "备注-2（异常情况）",width = 60)
    @ApiModelProperty(value = "备注-3(财务异常情况)")
    private String remmitExceptionRemark;

}
