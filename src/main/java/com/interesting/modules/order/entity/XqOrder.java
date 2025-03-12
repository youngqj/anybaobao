package com.interesting.modules.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Description: 订单表
 * @Author: interesting-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
@Data
@TableName("xq_order")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="xq_order对象", description="订单表")
public class XqOrder {

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
	private String id;
	/**出货序号*/
    @ApiModelProperty(value = "出货序号")
	private String shippingNum;
	/**订单类型(字典 1仓库订单 2客户订单)*/
    @ApiModelProperty(value = "订单类型(字典 1仓库订单 2客户订单)")
	private String orderType;
	/**客户id*/
    @ApiModelProperty(value = "客户id")
	private String customerId;

	@ApiModelProperty("仓库id")
	private String warehouseId;

	/**汇率*/
    @ApiModelProperty(value = "汇率")
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
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private java.util.Date invoiceDate;

	/**付款方式*/
    @ApiModelProperty(value = "付款方式")
	private String paymentMethod;
	/**要求发货日*/
    @ApiModelProperty(value = "要求发货日")
	private java.util.Date shippingDateRequired;
	/**送货地址*/
    @ApiModelProperty(value = "送货地址")
	private String deliveryAddress;
    /**
     * 送货地址
     */
    @ApiModelProperty(value = "客户送货地址id")
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
	private java.util.Date deliveryDateRequired;
	/**送货预约号*/
    @ApiModelProperty(value = "送货预约号")
	private String deliveryBookingNum;
	/**品管人员*/
    @ApiModelProperty(value = "品管人员")
	private String qualityControlPerson;
	/**跟单人员*/
    @ApiModelProperty(value = "跟单人员")
	private String orderFollowUpPerson;
	/**实际交货日*/
    @ApiModelProperty(value = "实际交货日")
	private java.util.Date actualDeliveryDate;
	/**质量问题说明*/
    @ApiModelProperty(value = "质量问题说明")
	private String qualityIssueDescription;
	/**生产日期*/
    @ApiModelProperty(value = "生产日期")
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
	/**创建人*/
	@ApiModelProperty(value = "创建人")
	private String createBy;
	/**创建时间*/
	@ApiModelProperty(value = "创建时间")
	private java.util.Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
	private String updateBy;
	/**更新时间*/
	@ApiModelProperty(value = "更新时间")
	private java.util.Date updateTime;
	/**逻辑删除 0否 1是*/
	@ApiModelProperty(value = "逻辑删除 0否 1是")
	private Integer izDelete;

	@ApiModelProperty(value = "跟单审核状态")
	private String followerAudit;

	@ApiModelProperty(value = "复审状态")
	private String repeatAudit;

	@ApiModelProperty(value = "审核人id")
	private String auditBy;

	@ApiModelProperty(value = "审核时间")
	private Date auditTime;

	@ApiModelProperty("完成状态")
	private String completeState;

	@ApiModelProperty("收汇到期日")
	private Date remittanceExpireDate;

	@ApiModelProperty("入库完成状态")
	private String enterState;

	@ApiModelProperty("出库完成状态")
	private String exitState;

	@ApiModelProperty("当前所有者id")
	private String transferBy;
    /**
     * 差旅费
     */
    @ApiModelProperty(value = "差旅费")
    private java.math.BigDecimal travelFee;

	@ApiModelProperty("是否提醒提醒收汇日期(0 否 1 是)")
	private Integer izRemind;

	@ApiModelProperty("是否提醒应付款到期日(0 否 1 是)")
	private Integer izRemindPayment;
}
