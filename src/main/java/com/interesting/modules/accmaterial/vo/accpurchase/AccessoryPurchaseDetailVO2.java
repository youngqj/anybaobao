package com.interesting.modules.accmaterial.vo.accpurchase;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 子表vo
 */
@Data
public class AccessoryPurchaseDetailVO2 {
    private String id;

    @ApiModelProperty("入库仓库id")
    private String toWarehouseId;

    @ApiModelProperty("入库仓库名称")
    private String toWarehouseName;

    @ApiModelProperty(value = "辅料id")
    private String accessoryId;

    @ApiModelProperty(value = "辅料名称")
    private String accessoryName;

    @ApiModelProperty(value = "尺寸")
    private String size;

    @ApiModelProperty("材质规格")
    private String materialSpecification;

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
}
