package com.interesting.modules.overseas.entity;

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
 * 海外仓-库存表
 * @TableName xq_inventory_overseas
 */
@Data
@TableName("xq_inventory_overseas")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqInventoryOverseas implements Serializable {

    private static final long SerialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "记录来源类型")
    private String sourceType;

    @ApiModelProperty(value = "源记录id")
    private String sourceId;


    @ApiModelProperty(value = "方向  1入 2出")
    private String direction;

    @ApiModelProperty(value = "数量")
    private Integer num;

    @ApiModelProperty(value = "辅料id")
    private String itemId;

    @ApiModelProperty(value = "仓库id")
    private String warehouseId;

    @ApiModelProperty(value = "仓库lot")
    private String warehouseLot;

    @ApiModelProperty(value = "相关日期（可手动选择）")
    private Date relativeTime;

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