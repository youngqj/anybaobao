package com.interesting.modules.accmaterial.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 辅料分类表
 * @TableName xq_accessory_category
 */
@Data
@TableName("xq_accessory_category")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqAccessoryCategory implements Serializable {

    private static final long SerialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty("pid")
    private String pid;

    @ApiModelProperty("是否含有子节点")
    private String hasChild;

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