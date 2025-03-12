package com.interesting.modules.commission.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddXqOrderCommissionDTO {
    	/**主键*/
        @ApiModelProperty(value = "主键")
    	private String id;
    	/**订单表id*/
        @ApiModelProperty(value = "订单表id")
    	private String orderId;
    	/**佣金公司*/
        @ApiModelProperty(value = "佣金公司")
    	private String company;
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
    	/**备注*/
        @ApiModelProperty(value = "备注")
    	private String notes;
}