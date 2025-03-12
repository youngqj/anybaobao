package com.interesting.modules.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import com.interesting.modules.freightInfo.vo.XqOrderFreightNoteVO;
import com.interesting.modules.notes.vo.XqProblemNoteVO;
import com.interesting.modules.order.vo.sub.*;
import com.interesting.modules.overseas.vo.EnterStorageDetailPageVO;
import com.interesting.modules.overseas.vo.ExitStorageDetailPageVO;
import com.interesting.modules.truck.vo.XqTruckInfoVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2024/1/24 9:54
 * @VERSION: V1.0
 */
@Data
public class ShowOrderVO {
    @ApiModelProperty("订单id")
    private String id;

    @ApiModelProperty(value = "出货序号")
    private String shippingNum;

    @ApiModelProperty(value = "订单类型(字典 1仓库订单 2客户订单)")
    private String orderType;

    @ApiModelProperty(value = "订单类型")
    private String orderTypeTxt;
    /**客户id*/
    @ApiModelProperty(value = "客户id")
    private String customerId;
    /**客户id*/
    @ApiModelProperty(value = "客户")
    private String customer;

    @ApiModelProperty("仓库id")
    private String warehouseId;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    /**汇率*/
    @ApiModelProperty(value = "汇率")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal exchangeRate;
    /**订单号*/
    @ApiModelProperty(value = "订单号")
    private String orderNum;
    /**原始订单号*/
    @ApiModelProperty(value = "原始订单号")
    private String originOrderNum;
    /**发票号*/
    @ApiModelProperty(value = "发票号")
    private String invoiceNum;
    /**发票日期*/
    @ApiModelProperty(value = "发票日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private java.util.Date invoiceDate;
    /**付款方式*/
    @ApiModelProperty(value = "付款方式id")
    private String paymentMethod;
    /**
     * 付款方式
     */
    @ApiModelProperty(value = "付款方式Name")
    private String paymentMethodName;
    /**要求发货日*/
    @ApiModelProperty(value = "要求发货日")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private java.util.Date shippingDateRequired;
    /**送货地址*/
    @ApiModelProperty(value = "送货地址")
    private String deliveryAddress;
    /**境外收货人*/
    @ApiModelProperty(value = "境外收货人")
    private String overseasReceiver;
    /**境内发货人*/
    @ApiModelProperty(value = "境内发货人")
    private String domesticSender;
    /**特殊要求*/
    @ApiModelProperty(value = "特殊要求")
    private String specialRequirements;
    /**要求交货日*/
    @ApiModelProperty(value = "要求交货日")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private java.util.Date deliveryDateRequired;
    /**送货预约号*/
    @ApiModelProperty(value = "送货预约号")
    private String deliveryBookingNum;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "转让人")
    private String transferBy;

    /**品管人员*/
    @ApiModelProperty(value = "品管人员")
    private String qualityControlPerson;

//    /**品管人员*/
//    @ApiModelProperty(value = "品管人员")
//    private String qualityControlPersonName;
//
    /**跟单人员*/
    @ApiModelProperty(value = "跟单人员")
    private String orderFollowUpPerson;
//
//    /**跟单人员*/
//    @ApiModelProperty(value = "跟单人员")
//    private String orderFollowUpPersonName;
    /**实际交货日*/
    @ApiModelProperty(value = "实际交货日")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private java.util.Date actualDeliveryDate;
    /**质量问题说明*/
    @ApiModelProperty(value = "质量问题说明")
    private String qualityIssueDescription;
    /**生产日期*/
    @ApiModelProperty(value = "生产日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private java.util.Date productionDate;
    /**打码内容*/
    @ApiModelProperty(value = "打码内容")
    private String maskedContent;
    /**订单质量问题说明*/
    @ApiModelProperty(value = "订单质量问题说明")
    private String orderQualityRemark;
    /**订单质量问题说明*/
    @ApiModelProperty(value = "订单质量问题说明")
    private String rawQualityRemark;
    /**收汇情况说明*/
    @ApiModelProperty(value = "收汇情况说明")
    private String remittanceRemark;
    /**备注*/
    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "差旅费")
    private java.math.BigDecimal travelFee;

    @ApiModelProperty(value = "复审状态")
    private String repeatAudit;

    @ApiModelProperty(value = "跟单审核状态")
    private String followerAudit;

    @ApiModelProperty("产品信息销售单价/?")
    private String productUnitName;


    /* 订单产品vo */
    @ApiModelProperty("产品信息列表")
    private List<XqOrderProdVO> orderProdVOS = Collections.emptyList();

    // 额外费用
    @ApiModelProperty("额外费用")
    private List<ListXqOrderExtraCostVO> xqOrderExtraCostVOS = Collections.emptyList();

    // 特殊情况说明
    @ApiModelProperty("特殊情况说明")
    private List<XqProblemNoteVO> orderNotes = Collections.emptyList();

    /* 佣金vo */
    @ApiModelProperty("佣金信息")
    private List<XqOrderComsVO> orderComsVOS = Collections.emptyList();





    /* 收汇到期日 */
    @ApiModelProperty("收汇到期日")
    private List<XqOrderRemiExpDateVO> remiExpDateVOS = Collections.emptyList();

    /* 收汇情况vo */
    @ApiModelProperty("收汇情况")
    private List<XqOrderRemiVO> orderRemiVOS = Collections.emptyList();

    @ApiModelProperty("中信保投保")
    private List<XqOrderCreditsInsuranceVO> orderCreditsInsuranceVOS = Collections.emptyList();

    @ApiModelProperty("收汇情况说明")
    private List<XqProblemNoteVO> remiNotes = Collections.emptyList();






    /* 原料采购vo */
    @ApiModelProperty("原料采购")
    private List<XqOrderRawVO> orderRawVOS = Collections.emptyList();

    /* 问题说明 */
    @ApiModelProperty("情况说明")
    private List<XqProblemNoteVO> rawNotes = Collections.emptyList();





    /* 辅料vo */
    @ApiModelProperty("辅料采购信息")
    private List<XqOrderAcsrVO> orderAcsrVOS = Collections.emptyList();







    @ApiModelProperty("货运信息")
    private XqOrderFretVO xqOrderFretVO;

    /* 货运费用（国内） */
    @ApiModelProperty("货运费用信息（国内）")
    private List<XqOrderFretCostVO> orderFretCostVOS = Collections.emptyList();

    /* 货运费用（国外） */
    @ApiModelProperty("货运费用信息（国外）")
    private List<XqOrderFretCostVO> orderFretCostVOS2 = Collections.emptyList();

    /* 货运备注信息 */
    @ApiModelProperty("货运备注")
    private List<XqOrderFreightNoteVO> orderFreightNoteVOS = Collections.emptyList();

    /* 退运费用 */
    @ApiModelProperty("退运模块信息")
    private List<XqOrderFretCostVO> orderFretCostReturnFeeVOS = Collections.emptyList();

    @ApiModelProperty("保险费用")
    private XqOrderInsuranceVO xqOrderInsuranceVO;





    /* 卡车信息 */
    @ApiModelProperty("卡车信息")
    private List<XqTruckInfoVO> truckInfos = Collections.emptyList();





    @ApiModelProperty("海外仓入库")
    private List<EnterStorageDetailPageVO> enterStorageDetailPageVOS = Collections.emptyList();





    @ApiModelProperty("海外仓出库")
    private List<ExitStorageDetailPageVO> exitStorageDetailPageVOS = Collections.emptyList();


//
//    // 合计
//    @ApiModelProperty("原料采购")
//    private List<JSONObject> sumXqOrderRawVO;
//    @ApiModelProperty("辅料采购")
//    private List<JSONObject> sumXqOrderAcsrVO;
//    @ApiModelProperty("国内")
//    private List<JSONObject> sumXqOrderFretCostInVO;
//    @ApiModelProperty("国外")
//    private List<JSONObject> sumXqOrderFretCostOutVO;
//    @ApiModelProperty("退运")
//    private List<JSONObject> sumXqOrderFretCostReturnVO;


}
