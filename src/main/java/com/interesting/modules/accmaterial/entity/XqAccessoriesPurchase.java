package com.interesting.modules.accmaterial.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 辅料采购表
 * @Author: interesting-boot
 * @Date:   2023-06-02
 * @Version: V1.0
 */
@Data
@TableName("xq_accessories_purchase")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="xq_accessories_purchase对象", description="辅料采购表")
public class XqAccessoriesPurchase {

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
	private String id;

	private String orderId;

	private Integer sortNum;

	/**产品id*/
	@ApiModelProperty(value = "产品id")
	private String productId;

	/**包装方式*/
    @ApiModelProperty(value = "包装方式")
	private String packaging;

	/**包装方式*/
	@ApiModelProperty(value = "包装方式单位id")
	private String packagingUnit;
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
    @ApiModelProperty(value = "辅料id")
	private String accessoryId;
	/**订单数量*/
    @ApiModelProperty(value = "订单数量")
	private Integer orderQuantity;
	/**采购单价*/
    @ApiModelProperty(value = "采购单价")
	private java.math.BigDecimal unitPrice;

	@ApiModelProperty("币种")
	private String currency;

	/**采购金额*/
    @ApiModelProperty(value = "采购金额")
	private java.math.BigDecimal purchaseAmount;
	/**税率*/
	@ApiModelProperty(value = "税率")
	private java.math.BigDecimal taxRate;
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
}
