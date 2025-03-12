package com.interesting.modules.accmaterial.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName xq_accessory_purchase_inventory_detail
 */
@TableName(value ="xq_accessory_purchase_inventory_detail")
@Data
public class XqAccessoryPurchaseInventoryDetail implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("来源记录id")
    private String sourceId;

    @ApiModelProperty("入库仓库id")
    private String toWarehouseId;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}