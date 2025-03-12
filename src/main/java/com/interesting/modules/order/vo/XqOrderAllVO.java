package com.interesting.modules.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import com.interesting.modules.freightInfo.dto.AddFreightNoteDTO;
import com.interesting.modules.freightInfo.vo.XqOrderFreightNoteVO;
import com.interesting.modules.notes.vo.XqProblemNoteVO;
import com.interesting.modules.order.vo.sub.*;
import com.interesting.modules.overseas.dto.InstUptExitStorageDetailItemDTO;
import com.interesting.modules.truck.vo.XqTruckInfoVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 整个面单详情vo
 */
@Data
public class XqOrderAllVO {
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

    /**
     * 送货地址
     */
    @ApiModelProperty(value = "送货地址Id")
    private String deliveryAddressId;
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

    /**
     * 差旅费
     */
    @ApiModelProperty(value = "差旅费")
    private java.math.BigDecimal travelFee;

    @ApiModelProperty(value = "复审状态")
    private String repeatAudit;

    // 额外费用
    @ApiModelProperty("额外费用")
    private List<ListXqOrderExtraCostVO> xqOrderExtraCostDTOS = Collections.emptyList();
    ;

    /* 订单产品vo */
    List<XqOrderProdVO> orderProdVOS = Collections.emptyList();

    /* 佣金vo */
    List<XqOrderComsVO> orderComsVOS = Collections.emptyList();

    /* 收汇情况vo */
    List<XqOrderRemiVO> orderRemiVOS = Collections.emptyList();

    List<XqOrderCreditsInsuranceVO> orderCreditsInsuranceVOS = Collections.emptyList();

    /* 原料采购vo */
    List<XqOrderRawVO> orderRawVOS = Collections.emptyList();

    /* 原料采购财务vo */
    List<XqOrderRawFinanceVO> orderRawFinanceVOS = Collections.emptyList();

    /* 辅料vo */
    List<XqOrderAcsrVO> orderAcsrVOS = Collections.emptyList();

    /* 货运费用（国内） */
    List<XqOrderFretCostVO> orderFretCostVOS = Collections.emptyList();

    /* 货运费用（国外） */
    List<XqOrderFretCostVO> orderFretCostVOS2 = Collections.emptyList();

    /* 退运费用 */
    List<XqOrderFretCostVO> orderFretCostReturnFeeVOS = Collections.emptyList();

    /* 货运信息 */
    List<XqOrderFretVO> orderFretVOS = Collections.emptyList();

    /* 保险费用 */
    List<XqOrderInsuranceVO> orderInsuranceVOS = Collections.emptyList();
    /* 货运备注信息 */
    List<XqOrderFreightNoteVO> orderFreightNoteVOS = Collections.emptyList();

    @ApiModelProperty(value = "跟单审核状态")
    private String followerAudit;

    /* 附件信息 */
//    List<ListXqFileVO> orderFiles1;
//    List<ListXqFileVO> orderFiles2;
//    List<ListXqFileVO> orderFiles3;
//    List<ListXqFileVO> orderFiles4;
//    List<ListXqFileVO> orderFiles5;
//    private String orderFilesId1;
//    private String orderFilesId2;
//    private String orderFilesId3;
//    private String orderFilesId4;
//    private String orderFilesId5;

    /* 问题说明 */
    List<XqProblemNoteVO> orderNotes = Collections.emptyList();
    List<XqProblemNoteVO> remiNotes = Collections.emptyList();
    List<XqProblemNoteVO> rawNotes = Collections.emptyList();


    /* 收汇到期日 */
    List<XqOrderRemiExpDateVO> remiExpDateVOS = Collections.emptyList();

    /* 卡车信息 */
    List<XqTruckInfoVO> truckInfos = Collections.emptyList();


    @ApiModelProperty(value = "海外仓出库信息")
    List<InstUptExitStorageDetailItemDTO> overseasExitDTOS = Collections.emptyList();

//    @ApiModelProperty("收汇到期日")
//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date remittanceExpireDate;
}
