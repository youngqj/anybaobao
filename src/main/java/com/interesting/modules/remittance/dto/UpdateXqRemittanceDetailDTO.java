package com.interesting.modules.remittance.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateXqRemittanceDetailDTO {
    	/**主键*/
        @ApiModelProperty(value = "主键")
    	private String id;
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
    	/**质量/折扣*/
        @ApiModelProperty(value = "质量/折扣")
    	private java.math.BigDecimal qualityDiscount;
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
    	/**投保日期*/
        @ApiModelProperty(value = "投保日期")
    	private java.util.Date insuranceEffectiveDate;
}