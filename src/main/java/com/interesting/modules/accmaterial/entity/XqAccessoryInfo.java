package com.interesting.modules.accmaterial.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName xq_accessory_info
 */
@Data
@TableName("xq_accessory_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqAccessoryInfo implements Serializable {

    private static final long SerialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "辅料名称")
    private String accessoryName;

    @ApiModelProperty(value = "尺寸")
    private String size;

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

    @ApiModelProperty("辅料分类id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String categoryId;

    @ApiModelProperty("材质规格")
    private String materialSpecification;
}