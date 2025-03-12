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
 * 海外仓-出库单子表
 * @TableName xq_overseas_exit_head
 */
@Data
@TableName("xq_overseas_exit_head")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqOverseasExitHead implements Serializable {

    private static final long SerialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "出库单号")
    private String warehouseExitNo;

    @ApiModelProperty(value = "来源订单号")
    private String orderNum;

    @ApiModelProperty(value = "单据日期")
    private Date receiptDate;

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

    @ApiModelProperty("审核状态")
    private String auditStatus;
}