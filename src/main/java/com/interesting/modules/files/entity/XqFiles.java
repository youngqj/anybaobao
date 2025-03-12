package com.interesting.modules.files.entity;

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
 * 附件表
 * @TableName xq_files
 */
@Data
@TableName("xq_files")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqFiles implements Serializable {

    private static final long SerialVersionUID = 1L;

    @ApiModelProperty(value = "树形节点id")
    private String nodeId;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "模块类型")
    private String moduleType;

    @ApiModelProperty(value = "文件路径")
    private String fileUrl;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "文件大小")
    private Integer fileSize;

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

    private String fileUid;
}