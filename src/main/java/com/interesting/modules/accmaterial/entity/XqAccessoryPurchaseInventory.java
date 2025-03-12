package com.interesting.modules.accmaterial.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName xq_accessory_purchase_inventory
 */
@TableName(value ="xq_accessory_purchase_inventory")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqAccessoryPurchaseInventory implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键id")
    private String id;

    /**
     * 采购合同号
     */
    @ApiModelProperty(value = "采购合同号")
    private String purchaseContractNo;

    /**
     * 采购日期
     */
    @ApiModelProperty(value = "采购日期")
    private Date purchaseDate;

    /**
     * 供应商id
     */
    @ApiModelProperty(value = "供应商id")
    private String supplierId;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("审核状态")
    private String auditStatus;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 逻辑删除 0否 1是
     */
    private Integer izDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}