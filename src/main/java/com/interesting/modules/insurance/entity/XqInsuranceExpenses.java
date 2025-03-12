package com.interesting.modules.insurance.entity;

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
 * @Description: 保险费用信息
 * @Author: interesting-boot
 * @Date:   2023-06-02
 * @Version: V1.0
 */
@Data
@TableName("xq_insurance_expenses")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="xq_insurance_expenses对象", description="保险费用信息")
public class XqInsuranceExpenses {

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
	private String id;

	private String orderId;
	/**货物保险公司*/
    @ApiModelProperty(value = "货物保险公司")
	private String insuranceCompany;
	/**货物保险费*/
    @ApiModelProperty(value = "货物保险费")
	private java.math.BigDecimal insuranceFee;
	/**备注*/
    @ApiModelProperty(value = "备注")
	private String remark;
	/**申请日期*/
    @ApiModelProperty(value = "申请日期")
	private java.util.Date applicationDate;
	/**财务确认金额*/
    @ApiModelProperty(value = "财务确认金额")
	private java.math.BigDecimal financeConfirmAmount;
	/**财务审核日期*/
    @ApiModelProperty(value = "财务审核日期")
	private java.util.Date financeAuditDate;

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

}
