package com.interesting.modules.rawmaterial.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddXqRawMaterialPurchaseDTO {
    	/**ID*/
        @ApiModelProperty(value = "ID")
    	private String id;
    	/**订单表id*/
        @ApiModelProperty(value = "订单表id")
    	private String orderId;
    	/**原料供应商id*/
        @ApiModelProperty(value = "原料供应商id")
    	private String supplierId;
    	/**采购合同号*/
        @ApiModelProperty(value = "采购合同号")
    	private String purchaseContract;
    	/**产品名称*/
        @ApiModelProperty(value = "产品名称")
    	private String productName;
    	/**重量*/
        @ApiModelProperty(value = "重量")
    	private java.math.BigDecimal weight;
    	/**币种*/
        @ApiModelProperty(value = "币种")
    	private String currency;
    	/**采购单价*/
        @ApiModelProperty(value = "采购单价")
    	private java.math.BigDecimal unitPrice;
    	/**采购金额*/
        @ApiModelProperty(value = "采购金额")
    	private java.math.BigDecimal purchaseAmount;
    	/**退税率*/
        @ApiModelProperty(value = "退税率")
    	private java.math.BigDecimal taxRefundRate;
    	/**退税金额*/
        @ApiModelProperty(value = "退税金额")
    	private java.math.BigDecimal taxRefundAmount;
    	/**采购备注*/
        @ApiModelProperty(value = "采购备注")
    	private String purchaseNote;
    	/**付款时间*/
        @ApiModelProperty(value = "付款时间")
    	private java.util.Date paymentTime;
    	/**付款金额*/
        @ApiModelProperty(value = "付款金额")
    	private java.math.BigDecimal paymentAmount;
    /**
     * 未付款金额
     */
    @ApiModelProperty(value = "未付款金额")
    private java.math.BigDecimal unPaymentAmount;
    	/**付款状态*/
        @ApiModelProperty(value = "付款状态")
    	private java.math.BigDecimal paymentStatus;
    	/**财务备注*/
        @ApiModelProperty(value = "财务备注")
    	private String financialNote;
}