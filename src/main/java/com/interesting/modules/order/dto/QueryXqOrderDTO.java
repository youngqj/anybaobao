package com.interesting.modules.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryXqOrderDTO {

    private String column = "create_time";

    private String order = "desc";

    private Integer pageNo = 1;

    private Integer pageSize = 10;

    @ApiModelProperty(value = "跟单审核状态")
    private Integer followerAudit;

    @ApiModelProperty(value = "收款状态")
    private Integer paymentStatus;

    @ApiModelProperty(value = "财务复审状态")
    private Integer repeatAudit;

    @ApiModelProperty(value = "付款状态")
    private Integer payStatus;


    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @ApiModelProperty(value = "海外客户订单号")
    private String customerOrderNum;

    @ApiModelProperty(value = "原始订单号")
    private String originOrderNum;

    @ApiModelProperty(value = "开船开始时间")
    private String etdStart;

    @ApiModelProperty(value = "开船结束时间")
    private String etdEnd;


    @ApiModelProperty(value = "收汇日期开始时间")
    private String expireDateStart;

    @ApiModelProperty(value = "收汇日结束始时间")
    private String expireDateEnd;

    @ApiModelProperty(value = "订单类型")
    private String orderType;

    @ApiModelProperty(value = "产品信息")
    private String productInfo;

    @ApiModelProperty("跟单人员")
    private String orderFollowUpPerson;

    @ApiModelProperty(value = "要求发货日开始")
    private String shippingDateRequiredBegin;

    @ApiModelProperty(value = "要求发货日结束")
    private String shippingDateRequiredEnd;

    @ApiModelProperty(value = "实际交货日开始")
    private String deliveryDateRequiredBegin;

    @ApiModelProperty(value = "实际交货日结束")
    private String deliveryDateRequiredEnd;

    @ApiModelProperty(value = "客户名称")
    private String customer;

    @ApiModelProperty(value = "提单号码")
    private String billOfLading;

    @ApiModelProperty(value = "货柜号码")
    private String containerNo;


}