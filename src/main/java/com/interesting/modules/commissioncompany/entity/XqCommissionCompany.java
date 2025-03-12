package com.interesting.modules.commissioncompany.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 佣金公司表
 * @TableName xq_commission_company
 */
@Data
@TableName("xq_commission_company")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqCommissionCompany implements Serializable {

    private static final long SerialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

//    @ApiModelProperty(value = "客户id")
//    private String customerId;

    @ApiModelProperty(value = "佣金公司名称")
    private String name;

    @ApiModelProperty(value = "佣金公司条件")
    private String requirements;

    @ApiModelProperty(value = "佣金比例")
    private BigDecimal percentage;

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

    private String templateId;
}