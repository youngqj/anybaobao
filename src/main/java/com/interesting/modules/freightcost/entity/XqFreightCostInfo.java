package com.interesting.modules.freightcost.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Description: 货运费用信息
 * @Author: interesting-boot
 * @Date:   2023-06-02
 * @Version: V1.0
 */
@Data
@TableName("xq_freight_cost_info")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="xq_freight_cost_info对象", description="货运费用信息")
public class XqFreightCostInfo {

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
	private String id;
	/**订单id*/
    @ApiModelProperty(value = "订单id")
	private String orderId;

    @ApiModelProperty(value = "产品id")
    private String productId;

    /**
     * 版面要求
     */
    @ApiModelProperty(value = "版面要求")
    private String layoutRequirements;

	/**订单id*/
	@ApiModelProperty(value = "供应商id")
	private String supplierId;

	/**订单id*/
	@ApiModelProperty(value = "客户id")
	private String customerId;

	/**代理商*/
    @ApiModelProperty(value = "代理商")
	private String agent;
	@ApiModelProperty("地址")
	private String address;
	/**备注*/
    @ApiModelProperty(value = "备注")
	private String remark;
	/**币种*/
    @ApiModelProperty(value = "币种")
	private String currency;
	/**价格*/
    @ApiModelProperty(value = "价格")
	private java.math.BigDecimal price;
	/**申请时间*/
    @ApiModelProperty(value = "申请时间")
	private java.util.Date applicationTime;

	/** 相关附件地址 */
	@ApiModelProperty(value = "相关附件地址，多个以逗号分割")
	private String filesUrl;

	/**财务确认金额*/
    @ApiModelProperty(value = "财务确认金额")
	private java.math.BigDecimal financeAmount;
	/**财务审核日期*/
    @ApiModelProperty(value = "财务审核日期")
	private java.util.Date financeAuditTime;
	/**费用类型（字典）*/
    @ApiModelProperty(value = "费用类型（字典）")
	private String feeType;
	/**是否为国内费用 1国内 0国外*/
    @ApiModelProperty(value = "是否为国内费用 1国内 0国外")
	private Integer izDomestic;
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

	/**是否为退运费用*/
	@ApiModelProperty(value = "是否为退运费用")
	private Integer izReturnFee;

	/**是否为退运费用*/
	@ApiModelProperty(value = "是否为默认带出  0-红色 1-蓝色")
	private Integer izDefaultColor;

	/**
	 * 付款时间
	 */
	@ApiModelProperty(value = "付款时间")
	private java.util.Date payTime;


	/**
	 * 付款金额
	 */
	@ApiModelProperty(value = "付款金额")
    private BigDecimal payMoney;


    /**
     * 应付金额
     */
    @ApiModelProperty(value = "应付金额")
    private BigDecimal payableAmount;


    /**
     * 已付金额
     */
    @ApiModelProperty(value = "已付金额")
    private BigDecimal paidAmount;

	/**仓库id*/
	@ApiModelProperty(value = "仓库id")
	private String warehouseId;

}
