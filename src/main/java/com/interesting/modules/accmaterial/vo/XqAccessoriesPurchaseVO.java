package com.interesting.modules.accmaterial.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class XqAccessoriesPurchaseVO {

	/**id*/
    @ApiModelProperty(value = "id")
	private String id;
	/**产品名称*/
    @ApiModelProperty(value = "产品名称")
	private String productName;
	/**包装方式*/
    @ApiModelProperty(value = "包装方式")
	private String packaging;
	/**版面要求*/
    @ApiModelProperty(value = "版面要求")
	private String layoutRequirements;
	/**辅料供应商id*/
    @ApiModelProperty(value = "辅料供应商id")
	private String supplierId;
	/**采购合同号*/
    @ApiModelProperty(value = "采购合同号")
	private String purchaseContractNo;
	/**辅料名称*/
    @ApiModelProperty(value = "辅料名称")
	private String accessoryName;
	/**尺寸*/
    @ApiModelProperty(value = "尺寸")
	private String size;
	/**订单数量*/
    @ApiModelProperty(value = "订单数量")
	private Integer orderQuantity;
	/**采购单价*/
    @ApiModelProperty(value = "采购单价")
	private java.math.BigDecimal unitPrice;
	/**采购金额*/
    @ApiModelProperty(value = "采购金额")
	private java.math.BigDecimal purchaseAmount;
	/**含税金额*/
    @ApiModelProperty(value = "含税金额")
	private java.math.BigDecimal taxIncludedAmount;
	/**采购备注栏*/
    @ApiModelProperty(value = "采购备注栏")
	private String purchaseNote;
	/**财务审核时间*/
    @ApiModelProperty(value = "财务审核时间")
	private java.util.Date financeAuditDate;
	/**财务审核金额*/
    @ApiModelProperty(value = "财务审核金额")
	private java.math.BigDecimal financeAuditAmount;
}
