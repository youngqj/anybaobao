package com.interesting.modules.reportform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.common.annotation.Excel;
import com.interesting.config.BigDecimalSerializer2;
import com.interesting.config.BigDecimalSerializer3;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;

@Data
public class SellProfitPageVO {
    @ApiModelProperty("序号")
    @Excel(name = "序号", width = 10)
    private Integer serialNumber;

    @ApiModelProperty(value = "ETD")
    @Excel(name = "ETD", width = 12)
//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd")
//    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private String etd;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "订单详情id")
    private String orderDetailId;

    @Excel(name = "客户", width = 42)
    @ApiModelProperty(value = "客户")
    private String customerName;

    /**订单号*/
    @Excel(name = "订单号", width = 12)
    @ApiModelProperty(value = "订单号")
    private String orderNum;


    @ApiModelProperty(value = "产品id")
    private String productId;


    /**
     * 产品品类
     */
    @ApiModelProperty(value = "产品品类id")
    private String productCategory;

    /**
     * 产品品类
     */
    @Excel(name = "产品品类", width = 12)
    @ApiModelProperty(value = "产品品类名称")
    private String category;
    @Excel(name = "产品名称", width = 15)
    @ApiModelProperty(value = "产品名称")
    private String productName;


    /**
     * 收汇额外费用
     */
    @ApiModelProperty(value = "收汇额外费用")
    @Excel(name = "收汇额外费用", width = 13)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal collectionAdditionalFees = BigDecimal.ZERO;

    /**总箱数*/
    @Excel(name = "销售数量", width = 10)
    @ApiModelProperty(value = "销售数量")
    private Integer totalBoxes;

    /**总箱数*/
    @Excel(name = "销售数量", width = 10)
    @ApiModelProperty(value = "销售总重")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalWeight;

    /**单价/箱*/
    @Excel(name = "销售单价", width = 10)
    @JsonSerialize(using = BigDecimalSerializer3.class)
    @ApiModelProperty(value = "销售单价")
    private java.math.BigDecimal pricePerBox = BigDecimal.ZERO;

    /**单价/磅*/
    @Excel(name = "单价/磅", width = 10)
    @JsonSerialize(using =BigDecimalSerializer3 .class)
    @ApiModelProperty(value = "单价/磅")
    private java.math.BigDecimal pricePerLb = BigDecimal.ZERO;

    /**销售金额*/
    @Excel(name = "销售总额", width = 15)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    @ApiModelProperty(value = "销售总额")
    private java.math.BigDecimal salesAmount = BigDecimal.ZERO;

    /**采购单价*/
    @Excel(name = "采购单价", width = 10)
    @ApiModelProperty(value = "采购单价", required = true)
    @JsonSerialize(using = BigDecimalSerializer3.class)
    private java.math.BigDecimal unitPrice = BigDecimal.ZERO;

    /**重量*/
    @ApiModelProperty(value = "采购数量")
    @Excel(name = "采购数量", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal weight = BigDecimal.ZERO;

    /**采购金额*/
    @ApiModelProperty(value = "采购总额")
    @Excel(name = "采购总额", width = 15)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal purchaseAmount = BigDecimal.ZERO;

    /** 客户佣金 */
    @ApiModelProperty(value = "客户佣金")
    @Excel(name = "客户佣金", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal customerCommission = BigDecimal.ZERO;

    /** 第三方进口佣金 */
    @ApiModelProperty(value = "第三方进口佣金")
    @Excel(name = "第三方进口佣金", width = 15)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal thirdPartImportCommission = BigDecimal.ZERO;

    /** 中间商佣金 */
    @ApiModelProperty(value = "中间商佣金")
    @Excel(name = "中间商佣金", width = 12)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal middleManCommission = BigDecimal.ZERO;

    /** 质量扣款 */
    @ApiModelProperty(value = "质量扣款")
    @Excel(name = "质量", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal qualityDeduction = BigDecimal.ZERO;

    /** 折扣 */
    @ApiModelProperty(value = "折扣/其他")
    @Excel(name = "折扣/其他", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal discount = BigDecimal.ZERO;

    /** 手续费 */
    @ApiModelProperty(value = "手续费")
    @Excel(name = "手续费", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal serviceCharge = BigDecimal.ZERO;

    /**信保保险费*/
    @ApiModelProperty(value = "信保保险费")
    @Excel(name = "信保保险费", width = 12)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal creditInsurancePremium= BigDecimal.ZERO;

    /**
     * 信保保险费
     */
    @ApiModelProperty(value = "总信保保险费")
    @Excel(name = "总信保保险费", width = 12)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalCreditInsurancePremium = BigDecimal.ZERO;

    /**保理利息*/
    @ApiModelProperty(value = "保理利息")
    @Excel(name = "保理利息", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal factoringInterest = BigDecimal.ZERO;

    /** 辅料采购金额（辅料合计1） */
    @ApiModelProperty(value = "辅料合计1")
    @Excel(name = "辅料合计1", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal accessoryPurchaseTotal = BigDecimal.ZERO;

    /** 辅料采购金额（辅料合计2） */
    @ApiModelProperty(value = "辅料合计2")
    @Excel(name = "辅料合计2", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal accessoryPurchaseTotal1 = BigDecimal.ZERO;


    /** 辅料采购金额（辅料合计3） */
    @ApiModelProperty(value = "辅料合计3")
    @Excel(name = "辅料合计3", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal accessoryPurchaseTotal2 = BigDecimal.ZERO;


    /**
     * 辅料采购金额（辅料合计1）
     */
    @ApiModelProperty(value = "辅料合计1")
    @Excel(name = "辅料合计1", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalAccessoryPurchaseTotal = BigDecimal.ZERO;

    /**
     * 辅料采购金额（辅料合计2）
     */
    @ApiModelProperty(value = "辅料合计2")
    @Excel(name = "辅料合计2", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalAccessoryPurchaseTotal1 = BigDecimal.ZERO;


    /**
     * 辅料采购金额（辅料合计3）
     */
    @ApiModelProperty(value = "辅料合计3")
    @Excel(name = "辅料合计3", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalAccessoryPurchaseTotal2 = BigDecimal.ZERO;


    /** CNY出岗前总费用 */
    @ApiModelProperty(value = "出港前总费用")
    @Excel(name = "出港前总费用", width = 13)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal beforeDepartureTotal = BigDecimal.ZERO;

    /*退运费*/
    @ApiModelProperty(value = "退运费")
    @Excel(name = "退运费", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal returnFee = BigDecimal.ZERO;

    /**货物保险费*/
    @ApiModelProperty(value = "货物保险费")
    @Excel(name = "货物保险费", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal insuranceFee = BigDecimal.ZERO;


    /**
     * 货物保险费
     */
    @ApiModelProperty(value = "总货物保险费")
    @Excel(name = "总货物保险费", width = 12)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalInsuranceFee = BigDecimal.ZERO;

    /** 国内海运费 */
    @ApiModelProperty(value = "国内海运费")
    @Excel(name = "国内海运费", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal domesticShippingFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "国内海运费颜色  0-红色 1-蓝色 2-价格为0灰色")
    private Integer domesticShippingFeeColor;

    /** 清关关税 */
    @ApiModelProperty(value = "清关关税")
    @Excel(name = "清关关税", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal customsClearanceTaxFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "清关关税颜色  0-红色 1-蓝色 2-价格为0灰色")
    private Integer customsClearanceTaxFeeColor;

    /** 国外清关费 */
    @ApiModelProperty(value = "国外清关费")
    @Excel(name = "国外清关费", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal foreignCustomsClearanceFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "国外清关费颜色  0-红色 1-蓝色 2-价格为0灰色")
    private Integer foreignCustomsClearanceFeeColor;

    /** 国外卡车费 */
    @ApiModelProperty(value = "国外卡车费")
    @Excel(name = "国外卡车费", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal foreignTruckFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "国外卡车费颜色  0-红色 1-蓝色 2-价格为0灰色")
    private Integer foreignTruckFeeColor;


    @ApiModelProperty(value = "货运额外费用")
    @Excel(name = "货运额外费用", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal extraFee = BigDecimal.ZERO;


    /** 差旅费用 */
    @ApiModelProperty(value = "差旅费用")
    @Excel(name = "差旅费用", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal travelFee = BigDecimal.ZERO;

    /**
     * 差旅费用
     */
    @ApiModelProperty(value = "总差旅费用")
    @Excel(name = "总差旅费用", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalTravelFee = BigDecimal.ZERO;

    /**退税金额*/
    @ApiModelProperty(value = "退税金额")
    @Excel(name = "退税金额", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal taxRefundAmount = BigDecimal.ZERO;

    /** 每磅成本 */
    @ApiModelProperty(value = "每磅成本")
    @Excel(name = "每磅/箱成本", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal costPerLb = BigDecimal.ZERO;

    /** 销售利润 */
    @ApiModelProperty(value = "销售利润")
    @Excel(name = "销售利润", width = 12)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal sellProfit = BigDecimal.ZERO;

    /** 拼柜总利润 */
    @ApiModelProperty(value = "拼柜总利润")
    @Excel(name = "拼柜总利润", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal lclSellProfit = BigDecimal.ZERO;

    /**汇率*/
    @ApiModelProperty(value = "汇率")
    @Excel(name = "汇率", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal exchangeRate = BigDecimal.ZERO;

    /** 辅料采购金额（辅料合计->除以汇率前）  */
    @ApiModelProperty(value = "辅料采购金额（辅料合计->除以汇率前）")
    @Excel(name = "辅料采购金额", width = 12)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal allCost = BigDecimal.ZERO;


    /**原料供应商id*/
    @ApiModelProperty(value = "原料供应商id")
    private String supplierId;

    /**原料供应商*/
    @ApiModelProperty(value = "原料供应商名称")
    @Excel(name = "原料供应商名称", width = 20)
    private String supplierName;

    /**
     * 原料币种
     */
    @ApiModelProperty(value = "原料币种")
    @Excel(name = "原料币种", width = 10)
    private String rawCurrency;

    /** 比例 */
    @ApiModelProperty(value = "比例")
    @Excel(name = "比例", width = 10)
    private Double ratio;

    /**
     * 比例
     */
    @ApiModelProperty(value = "订单个数")
    @Excel(name = "订单个数", width = 10)
    private Integer recordCount;

    /**
     * 总中间商佣金
     */
    @ApiModelProperty(value = "总中间商佣金")
    @Excel(name = "总中间商佣金", width = 12)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalMiddleManCommission = BigDecimal.ZERO;

    /**
     * 总客户佣金
     */
    @ApiModelProperty(value = "总客户佣金")
    @Excel(name = "总客户佣金", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalCustomerCommission = BigDecimal.ZERO;

    /**
     * 第三方进口佣金
     */
    @ApiModelProperty(value = "总第三方进口佣金")
    @Excel(name = "总第三方进口佣金", width = 15)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalThirdPartImportCommission = BigDecimal.ZERO;

    /**
     * 总质量
     */
    @ApiModelProperty(value = "总质量")
    @Excel(name = "总质量", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalQualityDeduction = BigDecimal.ZERO;


    /**
     * 总折扣
     */
    @ApiModelProperty(value = "总折扣")
    @Excel(name = "总折扣", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalDiscount = BigDecimal.ZERO;

    /**
     * 保理金额
     */
    @ApiModelProperty(value = "总保理金额")
    @Excel(name = "总保理金额", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalFactoringInterest = BigDecimal.ZERO;

    /**
     * 国内海运费
     */
    @ApiModelProperty(value = "总国内海运费")
    @Excel(name = "总国内海运费", width = 12)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalDomesticShippingFee = BigDecimal.ZERO;

    /**
     * 出港前总费用
     */
    @ApiModelProperty(value = "总出港前总费用")
    @Excel(name = "总出港前总费用", width = 12)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalBeforeDepartureTotal = BigDecimal.ZERO;


    /**
     * 出港前总费用
     */
    @ApiModelProperty(value = "总服务费")
    @Excel(name = "总服务费", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalServiceCharge = BigDecimal.ZERO;


    /**
     * 第三方进口
     */
    @ApiModelProperty(value = "总清关关税")
    @Excel(name = "总清关关税", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalCustomsClearanceTaxFee = BigDecimal.ZERO;

    /**
     * 第三方进口
     */
    @ApiModelProperty(value = "总国外清关费")
    @Excel(name = "总国外清关费", width = 12)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalForeignCustomsClearanceFee = BigDecimal.ZERO;
    /**
     * 第三方进口
     */
    @ApiModelProperty(value = "总额外费用")
    @Excel(name = "总额外费用", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalExtraFee = BigDecimal.ZERO;

    /**
     * 第三方进口
     */
    @ApiModelProperty(value = "总收汇额外费用")
    @Excel(name = "总收汇额外费用", width = 12)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalCollectionAdditionalFees = BigDecimal.ZERO;

    /**
     * 国外卡车费
     */
    @ApiModelProperty(value = "国外卡车费")
    @Excel(name = "国外卡车费", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalForeignTruckFee = BigDecimal.ZERO;


    @ApiModelProperty(value = "扣款金额")
    @Excel(name = "采购扣款费用", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal cutAmount = BigDecimal.ZERO;

}
