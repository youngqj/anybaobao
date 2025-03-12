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
import lombok.experimental.Accessors;

/**
 * 盘点录入
 * @TableName xq_inventory_check_detail
 */
@TableName(value ="xq_inventory_check_detail")
@Data
@Accessors(chain = true)
public class XqInventoryCheckDetail implements Serializable {

    @TableId
    private String id;

    private String sourceId;

    @ApiModelProperty("来源订单号")
    private String sourceRepNum;

    /**
     * 仓库id
     */
    private String warehouseId;

    /**
     * 辅料id
     */
    private String accessoryId;

    /**
     * 当前库存
     */
    private Integer currentInventory;

    /**
     * 实存数量
     */
    private Integer realInventory;

    /**
     * 盘点数量
     */
    private Integer checkInventory;

    /**
     * 币种
     */
    private String currency;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除 0否 1是
     */
    private Integer izDelete;

    private BigDecimal unitPrice;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}