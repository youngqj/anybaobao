package com.interesting.modules.warehouse.entity;

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
 * 仓库管理
 * @TableName xq_warehouse
 */
@Data
@TableName("xq_warehouse")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqWarehouse implements Serializable {

    private static final long SerialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "仓库编号")
    private String serialNum;

    @ApiModelProperty(value = "仓库名称")
    private String name;

    @ApiModelProperty(value = "仓库地址")
    private String address;

    @ApiModelProperty(value = "仓库类型（字典）")
    private String warehouseType;

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