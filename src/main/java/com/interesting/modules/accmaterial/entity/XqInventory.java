package com.interesting.modules.accmaterial.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.interesting.modules.warehouse.entity.XqWarehouse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 库存管理
 * @TableName xq_inventory
 */
@Data
@TableName("xq_inventory")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqInventory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "源记录id")
    private String sourceId;

    @ApiModelProperty(value = "来源记录类型")
    private String sourceType;

    @ApiModelProperty(value = "方向  1入 2出")
    private String direction;

    @ApiModelProperty(value = "数量")
    private Integer num;

    @ApiModelProperty(value = "辅料id")
    private String itemId;

    @ApiModelProperty(value = "仓库id")
    private String warehouseId;

    @ApiModelProperty(value = "币种")
    private String currency;

    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "相关日期（可手动选择）")
    private Date relativeTime;

    @ApiModelProperty("来源订单号")
    private String sourceRepNum;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除 0否 1是")
    private Integer izDelete;
}