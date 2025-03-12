package com.interesting.modules.reportform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer2;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/3/6 17:08
 * @Project: trade-manage
 * @Description:
 */

@Data
@TableName("xq_selling_profit")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqSellingProfit {

    private static final long SerialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "ETD")
    private Date etd;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "订单详情id")
    private String orderDetailId;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @ApiModelProperty(value = "产品id")
    private String productId;

    @ApiModelProperty(value = "产品品类id")
    private String productCategory;

    @ApiModelProperty(value = "产品品类名称")
    private String category;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "收汇额外费用")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal collectionAdditionalFees = BigDecimal.ZERO;

    @ApiModelProperty(value = "总箱数（销售数量）")
    private Integer totalBoxes;

    @ApiModelProperty(value = "总重（销售数量）")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalWeight;

    @JsonSerialize(using = BigDecimalSerializer2.class)
    @ApiModelProperty(value = "单价/箱（销售单价）")
    private BigDecimal pricePerBox = BigDecimal.ZERO;

    @JsonSerialize(using =BigDecimalSerializer2 .class)
    @ApiModelProperty(value = "单价/磅（销售单价）")
    private BigDecimal pricePerLb = BigDecimal.ZERO;

    @JsonSerialize(using = BigDecimalSerializer2.class)
    @ApiModelProperty(value = "销售金额（销售总额）")
    private BigDecimal salesAmount = BigDecimal.ZERO;

    @ApiModelProperty(value = "采购单价", required = true)
    private BigDecimal unitPrice = BigDecimal.ZERO;

    @ApiModelProperty(value = "重量（采购数量）")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal weight = BigDecimal.ZERO;

    @ApiModelProperty(value = "采购金额（采购总价）")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal purchaseAmount = BigDecimal.ZERO;

    @ApiModelProperty(value = "客户佣金")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal customerCommission = BigDecimal.ZERO;

    @ApiModelProperty(value = "第三方进口佣金")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal thirdPartImportCommission = BigDecimal.ZERO;

    @ApiModelProperty(value = "中间商佣金")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal middleManCommission = BigDecimal.ZERO;

    @ApiModelProperty(value = "质量扣款")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal qualityDeduction = BigDecimal.ZERO;

    @ApiModelProperty(value = "折扣")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal discount = BigDecimal.ZERO;

    @ApiModelProperty(value = "手续费")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal serviceCharge = BigDecimal.ZERO;

    @ApiModelProperty(value = "信保保险费")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal creditInsurancePremium= BigDecimal.ZERO;

    @ApiModelProperty(value = "总信保保险费")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalCreditInsurancePremium = BigDecimal.ZERO;

    @ApiModelProperty(value = "保理利息")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal factoringInterest = BigDecimal.ZERO;

    @ApiModelProperty(value = "辅料采购金额（辅料合计1）")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal accessoryPurchaseTotal = BigDecimal.ZERO;

    @ApiModelProperty(value = "辅料采购金额（辅料合计2）")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal accessoryPurchaseTotal1 = BigDecimal.ZERO;

    @ApiModelProperty(value = "辅料采购金额（辅料合计3）")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal accessoryPurchaseTotal2 = BigDecimal.ZERO;

    @ApiModelProperty(value = "辅料采购金额（辅料合计1）")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalAccessoryPurchaseTotal = BigDecimal.ZERO;

    @ApiModelProperty(value = "辅料采购金额（辅料合计2）")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalAccessoryPurchaseTotal1 = BigDecimal.ZERO;

    @ApiModelProperty(value = "辅料采购金额（辅料合计3）")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalAccessoryPurchaseTotal2 = BigDecimal.ZERO;

    @ApiModelProperty(value = "CNY出港前总费用）")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal beforeDepartureTotal = BigDecimal.ZERO;

    @ApiModelProperty(value = "货物保险费")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal insuranceFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "总货物保险费")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalInsuranceFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "国内海运费")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal domesticShippingFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "清关关税")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal customsClearanceTaxFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "国外清关费")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal foreignCustomsClearanceFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "国外卡车费")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal foreignTruckFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "额外费用")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal extraFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "差旅费用")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal travelFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "总差旅费用")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalTravelFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "退税金额")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal taxRefundAmount = BigDecimal.ZERO;

    @ApiModelProperty(value = "每磅成本")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal costPerLb = BigDecimal.ZERO;

    @ApiModelProperty(value = "销售利润")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal sellProfit = BigDecimal.ZERO;

    @ApiModelProperty(value = "拼柜总利润")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal lclSellProfit = BigDecimal.ZERO;

    @ApiModelProperty(value = "汇率")
    private BigDecimal exchangeRate = BigDecimal.ZERO;

    @ApiModelProperty(value = "辅料采购金额（辅料合计->除以汇率前）")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal allCost = BigDecimal.ZERO;

    @ApiModelProperty(value = "原料供应商id")
    private String supplierId;

    @ApiModelProperty(value = "原料供应商名称")
    private String supplierName;

    @ApiModelProperty(value = "原料币种")
    private String rawCurrency;

    @ApiModelProperty(value = "比例")
    private Double ratio;

    @ApiModelProperty(value = "订单个数")
    private Integer recordCount;

    @ApiModelProperty(value = "总中间商佣金")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalMiddleManCommission = BigDecimal.ZERO;

    @ApiModelProperty(value = "总客户佣金")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalCustomerCommission = BigDecimal.ZERO;

    @ApiModelProperty(value = "总质量")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalQualityDeduction = BigDecimal.ZERO;

    @ApiModelProperty(value = "总折扣")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalDiscount = BigDecimal.ZERO;

    @ApiModelProperty(value = "总保理金额")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalFactoringInterest = BigDecimal.ZERO;

    @ApiModelProperty(value = "总国内海运费")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalDomesticShippingFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "总出港前总费用")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalBeforeDepartureTotal = BigDecimal.ZERO;

    @ApiModelProperty(value = "总服务费")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalServiceCharge = BigDecimal.ZERO;

    @ApiModelProperty(value = "总清关关税")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalCustomsClearanceTaxFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "总国外清关费")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalForeignCustomsClearanceFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "总额外费用")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalExtraFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "总收汇额外费用")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalCollectionAdditionalFees = BigDecimal.ZERO;

    @ApiModelProperty(value = "国外卡车费")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalForeignTruckFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除 0否 1是")
    private Integer izDelete;

    @ApiModelProperty(value = "退运费")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal returnFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "总第三方进口佣金")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal totalThirdPartImportCommission = BigDecimal.ZERO;

    @ApiModelProperty(value = "扣款金额")
    @JsonSerialize(using = BigDecimalSerializer2.class)
    private BigDecimal cutAmount = BigDecimal.ZERO;



}
