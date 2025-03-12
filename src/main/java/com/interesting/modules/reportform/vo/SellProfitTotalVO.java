package com.interesting.modules.reportform.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.common.annotation.Excel;
import com.interesting.config.BigDecimalSerializer2;
import com.interesting.config.BigDecimalSerializer3;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/2/23 11:34
 * @Project: trade-manage
 * @Description:
 */
@Data
public class SellProfitTotalVO extends SumVO{

    @Excel(name = "销售利润合计")
    @ApiModelProperty("销售利润合计")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalSellProfit = BigDecimal.ZERO;

    @Excel(name = "销售合计")
    @ApiModelProperty("销售合计")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalSalesAmount = BigDecimal.ZERO;

    /**采购金额*/
    @ApiModelProperty(value = "采购总额合计")
    @Excel(name = "采购总额合计", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalPurchaseAmount = BigDecimal.ZERO;

    /*退运费*/
    @ApiModelProperty(value = "退运费合计")
    @Excel(name = "退运费合计", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalReturnFee = BigDecimal.ZERO;

    /** 额外费用 */
    @ApiModelProperty(value = "额外费用合计")
    @Excel(name = "额外费用合计", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalExtraFee = BigDecimal.ZERO;

    /** 客户佣金 */
    @ApiModelProperty(value = "客户佣金合计")
    @Excel(name = "客户佣金合计", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalcCstomerCommission = BigDecimal.ZERO;

    /** 第三方进口佣金 */
    @ApiModelProperty(value = "第三方进口佣金合计")
    @Excel(name = "第三方进口佣金合计", width = 15)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalThirdPartImportCommission = BigDecimal.ZERO;

    /** 中间商佣金 */
    @ApiModelProperty(value = "中间商佣金合计")
    @Excel(name = "中间商佣金合计", width = 12)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalMiddleManCommission = BigDecimal.ZERO;

    /** 质量扣款 */
    @ApiModelProperty(value = "质量扣款合计")
    @Excel(name = "质量扣款合计", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalQualityDeduction = BigDecimal.ZERO;

    /** 折扣 */
    @ApiModelProperty(value = "折扣/其他合计")
    @Excel(name = "折扣/其他合计", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalDiscount = BigDecimal.ZERO;

    /** 手续费 */
    @ApiModelProperty(value = "手续费合计")
    @Excel(name = "手续费合计", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalServiceCharge = BigDecimal.ZERO;

    /**信保保险费*/
    @ApiModelProperty(value = "信保保险费合计")
    @Excel(name = "信保保险费合计", width = 12)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalCreditInsurancePremium= BigDecimal.ZERO;

    /**保理利息*/
    @ApiModelProperty(value = "保理利息合计")
    @Excel(name = "保理利息合计", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalFactoringInterest = BigDecimal.ZERO;

    /** 辅料采购金额（辅料合计1） */
    @ApiModelProperty(value = "辅料合计1合计")
    @Excel(name = "辅料合计1合计", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalAccessoryPurchaseTotal = BigDecimal.ZERO;

    /** 辅料采购金额（辅料合计2） */
    @ApiModelProperty(value = "辅料合计2合计")
    @Excel(name = "辅料合计2合计", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalAccessoryPurchaseTotal1 = BigDecimal.ZERO;

    /** CNY出岗前总费用 */
    @ApiModelProperty(value = "出港前总费用合计")
    @Excel(name = "出港前总费用合计", width = 13)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalBeforeDepartureTotal = BigDecimal.ZERO;


    /**货物保险费*/
    @ApiModelProperty(value = "货物保险费合计")
    @Excel(name = "货物保险费合计", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalInsuranceFee = BigDecimal.ZERO;


    /** 国内海运费 */
    @ApiModelProperty(value = "国内海运费合计")
    @Excel(name = "国内海运费合计", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalDomesticShippingFee = BigDecimal.ZERO;

    /** 清关关税 */
    @ApiModelProperty(value = "清关关税合计")
    @Excel(name = "清关关税合计", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalCustomsClearanceTaxFee = BigDecimal.ZERO;


    /** 国外清关费 */
    @ApiModelProperty(value = "国外清关费合计")
    @Excel(name = "国外清关费合计", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalForeignCustomsClearanceFee = BigDecimal.ZERO;



    /** 国外卡车费 */
    @ApiModelProperty(value = "国外卡车费合计")
    @Excel(name = "国外卡车费合计", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalForeignTruckFee = BigDecimal.ZERO;

    /**
     * 差旅费用
     */
    @ApiModelProperty(value = "总差旅费用合计")
    @Excel(name = "总差旅费用合计", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalTravelFee = BigDecimal.ZERO;

    /**退税金额*/
    @ApiModelProperty(value = "退税金额合计")
    @Excel(name = "退税金额合计", width = 10)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalTaxRefundAmount = BigDecimal.ZERO;


    /** 拼柜总利润 */
    @ApiModelProperty(value = "拼柜总利润合计")
    @Excel(name = "拼柜总利润合计", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalLclSellProfit = BigDecimal.ZERO;


    /**
     * 第三方进口
     */
    @ApiModelProperty(value = "总收汇额外费用合计")
    @Excel(name = "总收汇额外费用合计", width = 12)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private java.math.BigDecimal totalCollectionAdditionalFees = BigDecimal.ZERO;

    /**总箱数*/
    @Excel(name = "采购数量", width = 10)
    @ApiModelProperty(value = "采购数量")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalWeight;

    @ApiModelProperty(value = "扣款金额")
    @Excel(name = "扣款金额", width = 11)
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal cutAmount = BigDecimal.ZERO;

}
