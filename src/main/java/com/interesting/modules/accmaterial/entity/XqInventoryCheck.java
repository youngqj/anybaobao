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
 * 盘点录入
 * @TableName xq_inventory_check
 */
@Data
@TableName("xq_inventory_check")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqInventoryCheck implements Serializable {

    private static final long SerialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "单据编号")
    private String repNum;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "盘点人id")
    private String checkUserId;

    @ApiModelProperty(value = "盘点时间")
    private Date checkTime;

    @ApiModelProperty(value = "仓库id")
    private String warehouseId;

    @ApiModelProperty(value = "审核状态")
    private String auditStatus;

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