package com.interesting.modules.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class XqOrderExportVO {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "客户")
    private String customer;

    @ApiModelProperty("送货地址")
    private String deliveryAddress;
//
//    @ApiModelProperty(value = "货运信息里国外费用的代理商")
//    private List<XqOrderExportFretVO> notifyParty;

    @ApiModelProperty(value = "代理商名称")
    private String agentName;

    @ApiModelProperty(value = "从装运港到目的港")
    private String transportDetails;

    @ApiModelProperty(value = "发票日期")
    private String invoiceDate;

    @ApiModelProperty(value = "发票号")
    private String invoiceNo;

    @ApiModelProperty(value = "ETD")
    private String etd;

    @ApiModelProperty(value = "eta")
    private String eta;

    @ApiModelProperty(value = "货柜号码")
    private String containerNo;

    @ApiModelProperty(value = "提单号码")
    private String billOfLading;

    @ApiModelProperty(value = "航名航次")
    private String voyageNumber;

    @ApiModelProperty(value = "商品信息")
    private List<XqOrderExportProductVO> xqOrderExportProductVOS;

    @ApiModelProperty("商品信息重量单位")
    private String weightUnitTxt;
    @ApiModelProperty("商品信息重量转换")
    private BigDecimal unitTransfer =BigDecimal.ZERO;
    @ApiModelProperty("商品信息币种")
    private String currency;


    @ApiModelProperty(value = "PO号")
    private String orderNum;

    @ApiModelProperty(value = "总磅数")
    private String sumWeight;

    @ApiModelProperty(value = "销售总价USD")
    private String sumSalesAmount;

    @ApiModelProperty(value = "销售总额转换成英文表达式")
    private String totalAmount;

    @ApiModelProperty(value = "付款方式")
    private String termsOfPayment;

    @ApiModelProperty(value = "生产日期")
    private String productionDate;

    @ApiModelProperty(value = "净重")
    private BigDecimal netWeight;

    @ApiModelProperty("净重（指定质量单位）")
    private BigDecimal netWeightByTransferUnit;

    @ApiModelProperty(value = "毛重")
    private BigDecimal grossWeight;

    @ApiModelProperty("毛重（指定质量单位）")
    private BigDecimal grossWeightByTransferUnit;

    @ApiModelProperty(value = "体积")
    private String sumVolume;

    @ApiModelProperty(value = "总毛重")
    private BigDecimal allGrossWeight;

}
