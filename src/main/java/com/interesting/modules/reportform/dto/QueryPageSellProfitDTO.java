package com.interesting.modules.reportform.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryPageSellProfitDTO {
    private String column = "create_time";

    private String order = "desc";
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    private String reportFormTimeOrder;

    private String exportColumn;

    @ApiModelProperty(value = "ETD开始")
    private String etdBegin;

    @ApiModelProperty(value = "ETD结束")
    private String etdEnd;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**订单号*/
    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @ApiModelProperty(value = "订单id",hidden = true)
    private String orderId;

    /**
     * 产品品类
     */
    @ApiModelProperty(value = "产品品类")
    private String productCategory;

//    @ApiModelProperty(value = "销售利润开始")
//    private String sellProfitStart;
//
//    @ApiModelProperty(value = "销售利润结束")
//    private String sellProfitEnd;
//
//    @ApiModelProperty(value = "拼柜利润开始")
//    private String lclSellProfitStart;
//
//    @ApiModelProperty(value = "拼柜利润结束")
//    private String lclSellProfitEnd;
//
//    @ApiModelProperty(value = "额外费用开始")
//    private String extraFeeStart;
//
//    @ApiModelProperty(value = "额外费用结束")
//    private String extraFeeEnd;
//
//    @ApiModelProperty(value = "保理利息开始")
//    private String factoringInterestStart;
//
//    @ApiModelProperty(value = "保理利息结束")
//    private String factoringInterestEnd;
//
//    @ApiModelProperty(value = "销售数量开始")
//    private String totalBoxesStart;
//
//    @ApiModelProperty(value = "销售数量结束")
//    private String totalBoxesEnd;
//
//
//    @ApiModelProperty(value = "销售总金额开始")
//    private String salesAmountStart;
//
//    @ApiModelProperty(value = "销售总金额结束")
//    private String salesAmountEnd;
//
//    @ApiModelProperty(value = "采购数量开始")
//    private String weightStart;
//
//    @ApiModelProperty(value = "采购数量结束")
//    private String weightEnd;
//
//    @ApiModelProperty(value = "采购总金额开始")
//    private String purchaseAmountStart;
//
//    @ApiModelProperty(value = "采购总金额结束")
//    private String purchaseAmountEnd;


}
