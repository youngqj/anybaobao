package com.interesting.modules.commission.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 佣金表
 * @Author: interesting-boot
 * @Date:   2023-06-02
 * @Version: V1.0
 */
@Data
@TableName("xq_order_commission")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="xq_order_commission对象", description="佣金表")
public class XqOrderCommission {

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
	private String id;

	/**客户订单号*/
	@ApiModelProperty(value = "客户订单号")
	private String customerOrderNum;

	/**订单表id*/
    @ApiModelProperty(value = "订单表id")
	private String orderId;
	/**佣金公司*/
    @ApiModelProperty(value = "佣金公司名称")
	private String companyName;
	/**佣金条件*/
    @ApiModelProperty(value = "佣金条件")
	private String requirements;
	/**佣金比例*/
    @ApiModelProperty(value = "佣金比例")
	private java.math.BigDecimal percentage;
	/**佣金金额*/
    @ApiModelProperty(value = "佣金金额")
	private java.math.BigDecimal amount;
	/**申请日期*/
    @ApiModelProperty(value = "申请日期")
	private java.util.Date applicationDate;
	/**财务确认金额*/
    @ApiModelProperty(value = "财务确认金额")
	private java.math.BigDecimal financeConfirmedAmount;
	/**财务审核日期*/
    @ApiModelProperty(value = "财务审核日期")
	private java.util.Date financeAuditDate;
//b
	/**备注*/
    @ApiModelProperty(value = "备注")
	private String notes;
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

	@ApiModelProperty("佣金类型（字典）")
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private String commissionType;

	/**佣金参数*/
	@ApiModelProperty(value = "佣金参数")
	private java.math.BigDecimal param;
}
