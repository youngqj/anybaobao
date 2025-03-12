package com.interesting.modules.unit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 单位表
 * @TableName xq_unit
 */
@Data
@TableName("xq_unit")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqUnit implements Serializable {

    private static final long SerialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "单位名称")
    private String name;

    @ApiModelProperty(value = "单位描述")
    private String description;

    @ApiModelProperty(value = "(字典) 单位类型，字典表可查看单位类型")
    private String unitType;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "逻辑删除 0否 1是")
    private Integer izDelete;
}