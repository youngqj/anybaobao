package com.interesting.modules.overseas.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@TableName("xq_overseas_transfer")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqOverseasTransfer {
    private static final long SerialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "单据编号")
    private String repNum;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "盘点人id")
    private String transferUserId;

    @ApiModelProperty(value = "盘点时间")
    private Date transferTime;

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
