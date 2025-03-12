package com.interesting.modules.rawmaterial.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName xq_payment_detail
 */
@Data
@TableName("xq_payment_detail")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqPaymentDetail implements Serializable {

    private static final long SerialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "来源记录id")
    private String sourceId;

    @ApiModelProperty(value = "付款金额")
    private BigDecimal payMoney;

    @ApiModelProperty(value = "付款时间")
    private Date payTime;

    @ApiModelProperty(value = "付款备注")
    private String payRemark;

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

    @ApiModelProperty(value = "类型 0付款 1扣款")
    private Integer type;

    @ApiModelProperty(value = "预付款id")
    private String prepaymentsId;
}