package com.interesting.modules.remittance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @TableName xq_remittance_expire
 */
@TableName(value ="xq_remittance_expire")
@ApiModel(value="xq_remittance_expire", description="收汇到期日表")
@Data
public class XqRemittanceExpire implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    private String orderId;

    @ApiModelProperty("金额")
    private BigDecimal price;

    /**
     * 收款到期日
     */
    @ApiModelProperty(value = "收款到期日")
    private Date remittanceExpireDate;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty("是否默认生成 0 否 1 是")
    private Integer izDefault;


    /**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**创建时间*/
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
    /**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    /**更新时间*/
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
    /**逻辑删除 0否 1是*/
    @ApiModelProperty(value = "逻辑删除 0否 1是")
    private Integer izDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}