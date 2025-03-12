package com.interesting.modules.freightcost.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class XqFreightCostInfoVO {

	/**id*/
    @ApiModelProperty(value = "id")
	private String id;
	/**订单id*/
    @ApiModelProperty(value = "订单id")
	private String orderId;
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
	/**财务确认金额*/
    @ApiModelProperty(value = "财务确认金额")
	private java.math.BigDecimal financeAmount;
	/**财务审核日期*/
    @ApiModelProperty(value = "财务审核日期")
	private java.util.Date financeAuditTime;
	/**费用类型（字典）*/
    @ApiModelProperty(value = "费用类型（字典）")
	private String feeType;

	/**费用类型（字典）*/
	@ApiModelProperty(value = "费用类型")
	private String feeTypeTxt;


	/**是否为国内费用 1国内 0国外*/
    @ApiModelProperty(value = "是否为国内费用 1国内 0国外")
	private Integer izDomestic;

	/** 相关附件地址 */
	@ApiModelProperty(value = "相关附件地址，多个以逗号分割")
	private String filesUrl;

	@ApiModelProperty(value = "创建人")
	private String createBy;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;


    /**
     * 应付金额
     */
    @ApiModelProperty(value = "应付金额")
    private BigDecimal payableAmount;


    /**
     * 付款时间
     */
    @ApiModelProperty(value = "已付金额")
    private BigDecimal paidAmount;

}
