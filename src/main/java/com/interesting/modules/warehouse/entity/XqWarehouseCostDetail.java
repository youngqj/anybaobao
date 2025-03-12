package com.interesting.modules.warehouse.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 仓储费明细
 * @TableName xq_warehouse_cost_detail
 */
@Data
@TableName("xq_warehouse_cost_detail")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqWarehouseCostDetail implements Serializable {

    private static final long SerialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "仓库Id")
    private String warehouseId;

    @ApiModelProperty(value = "仓库lot")
    private String warehouseLot;

    @ApiModelProperty(value = "第几月")
    private Integer months;

    @ApiModelProperty(value = "费用")
    private BigDecimal expense;

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