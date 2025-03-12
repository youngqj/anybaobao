package com.interesting.modules.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 列表vo
 */
@Data
public class XqOrderVO {

	/**主键*/
    @ApiModelProperty(value = "主键")
	private String id;

	@ApiModelProperty(value = "跟单审核状态")
	private String followerAudit;

//	/**出货序号*/
//    @ApiModelProperty(value = "出货序号")
//	private String shippingNum;

	/**订单类型(字典 1仓库订单 2客户订单)*/
    @ApiModelProperty(value = "订单类型(字典 1仓库订单 2客户订单)")
	private String orderType;

	@ApiModelProperty(value = "订单类型")
	private String orderTypeTxt;

	/**客户id*/
    @ApiModelProperty(value = "客户id")
	private String customerId;


    @ApiModelProperty(value = "收款状态：1 未收款 2 部分收款 3 全额收款")
    private Integer paymentStatus;


    @ApiModelProperty(value = "付款状态")
    private Integer payStatus;

	/**客户id*/
	@ApiModelProperty(value = "客户名称")
	private String customer;

	/**订单号*/
    @ApiModelProperty(value = "订单号")
	private String orderNum;

	/**
	 * 订单号
	 */
	@ApiModelProperty(value = "订单号")
	private String originOrderNum;

    /**
     * 客户订单号
     */
    @ApiModelProperty(value = "客户订单号")
    private String customerOrderNum;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "要求发货日")
	private java.util.Date shippingDateRequired;

	/**品管人员*/
	@ApiModelProperty(value = "品管人员")
	private String qualityControlPerson;

//	/**品管人员*/
//	@ApiModelProperty(value = "品管人员")
//	private String qualityControlPersonName;

	/**跟单人员*/
	@ApiModelProperty(value = "跟单人员")
	private String orderFollowUpPerson;

//	/**跟单人员*/
//	@ApiModelProperty(value = "跟单人员")
//	private String orderFollowUpPersonName;

	/**要求交货日*/
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "要求交货日")
	private java.util.Date deliveryDateRequired;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "实际交货日")
	private java.util.Date actualDeliveryDate;

	@ApiModelProperty(value = "提单号码")
	private String billOfLading;

	@ApiModelProperty(value = "货柜号码")
	private String containerNo;


	/**
     * 要求交货日
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "etd")
    private java.util.Date etd;


    /**
     * 产品信息
     */
    @ApiModelProperty(value = "产品信息")
    private String productInfo;

    /**
     * 产品信息
     */
    @ApiModelProperty(value = "原料供应商名称")
    private String suppierName;


	/**创建人*/
	@ApiModelProperty(value = "创建人")
	private String createBy;
	/**创建时间*/
	@ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;

//	/**原始订单号*/
//	@ApiModelProperty(value = "原始订单号")
//	private String originOrderNum;

	@ApiModelProperty(value = "复审状态")
	private String repeatAudit;

	@ApiModelProperty("当前所有者id")
	private String transferBy;

	@ApiModelProperty("是否有编辑按钮 0 无 1 有")
	private Integer hasEditButton = 0;

	@ApiModelProperty("是否有转让按钮 0 无 1 有")
	private Integer hasTransferButton = 0;

	@ApiModelProperty(value = "发票号")
	private String invoiceNum;
}
