package com.interesting.modules.remittance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 收汇情况表
 * @Author: interesting-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
@Data
@TableName("xq_remittance_detail")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="xq_remittance_detail对象", description="收汇情况表")
public class XqRemittanceDetail {

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
	private String id;

	@ApiModelProperty(value = "订单号")
	private String orderNum;

	/**订单表id*/
    @ApiModelProperty(value = "订单表id")
	private String orderId;
	/**收汇日期*/
    @ApiModelProperty(value = "收汇日期")
	private java.util.Date remittanceDate;
	/**收汇金额*/
    @ApiModelProperty(value = "收汇金额")
	private java.math.BigDecimal remittanceAmount;
	/**手续费*/
    @ApiModelProperty(value = "手续费")
	private java.math.BigDecimal serviceCharge;
	/**保理利息*/
    @ApiModelProperty(value = "保理利息")
	private java.math.BigDecimal factoringInterest;
	/**保理利息*/
	@ApiModelProperty(value = "保理金额")
	private java.math.BigDecimal factoringMoney;

    @ApiModelProperty(value = "折扣")
	private java.math.BigDecimal discount;

	@ApiModelProperty(value = "质量")
	private java.math.BigDecimal quality;

	/**未收款*/
    @ApiModelProperty(value = "未收款")
	private java.math.BigDecimal outstandingReceivables;
	/**未收汇比例*/
    @ApiModelProperty(value = "未收汇比例")
	private java.math.BigDecimal outstandingRemittanceRatio;
	/**备注*/
    @ApiModelProperty(value = "备注")
	private String remarks;
	/**信保保险费*/
    @ApiModelProperty(value = "信保保险费")
	private java.math.BigDecimal creditInsurancePremium;
	/**汇率*/
	@ApiModelProperty(value = "汇率")
	private java.math.BigDecimal exchangeRate;
	@ApiModelProperty(value = "汇率转换后 信保保险费")
	private java.math.BigDecimal creditInsurancePremiumConvert;
	/**投保日期*/
    @ApiModelProperty(value = "投保日期")
	private java.util.Date insuranceEffectiveDate;
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
     * 币种
     */
    @ApiModelProperty(value = "币种")
    private String currency;
}
