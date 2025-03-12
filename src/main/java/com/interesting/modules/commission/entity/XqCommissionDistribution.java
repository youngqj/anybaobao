package com.interesting.modules.commission.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("xq_commission_distribution")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqCommissionDistribution {
    private static final long SerialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "订单Id")
    private String orderId;

    @ApiModelProperty(value = "订单详情Id")
    private String orderDetailId;

    @ApiModelProperty(value = "产品id")
    private String productId;

    @ApiModelProperty(value = "佣金id")
    private String commissionId;

    @ApiModelProperty(value = "佣金金额")
    private BigDecimal commissionPrice;

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
