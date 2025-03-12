package com.interesting.modules.accmaterial.dto.accpurchase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 子表dto
 */
@Data
public class InstUptAccPurchaseDTO2 {
    private String id;

    @ApiModelProperty(value = "入库仓库id", required = true)
    @NotBlank(message = "入库仓库id不能为空")
    private String toWarehouseId;

//    @ApiModelProperty(value = "辅料id")
//    private String accessoryId;

    @ApiModelProperty(value = "辅料名称", required = true)
    @NotBlank(message = "辅料名称不能为空")
    private String accessoryName;

    @ApiModelProperty(value = "尺寸", required = true)
    @NotBlank(message = "尺寸不能为空")
    private String size;

    @ApiModelProperty(value = "材质规格", required = true)
    @NotBlank(message = "材质规格不能为空")
    private String materialSpecification;

    /**订单数量*/
    @ApiModelProperty(value = "订单数量", required = true)
    @NotNull(message = "订单数量不能为空")
    private Integer orderQuantity;

    /**采购单价*/
    @ApiModelProperty(value = "采购单价", required = true)
    @NotNull(message = "采购单价不能为空")
    private java.math.BigDecimal unitPrice;

    @ApiModelProperty(value = "币种", required = true)
    @NotBlank(message = "币种不能为空")
    private String currency;

    /**采购金额*/
    @ApiModelProperty(value = "采购金额", required = true)
    @NotNull(message = "采购金额不能为空")
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
}
