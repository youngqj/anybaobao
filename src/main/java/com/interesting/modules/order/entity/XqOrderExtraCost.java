package com.interesting.modules.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 付款方式表(XqOrderExtraCost)实体类
 *
 * @author 郭征宇
 * @since 2023-09-18 14:48:40
 */

@Data
@EqualsAndHashCode
@ApiModel(value = "XqOrderExtraCost表",description = ("付款方式表"))
@TableName("xq_order_extra_cost")
public class XqOrderExtraCost implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("主键")
    private String id;
    /**
     * 订单id
     */     
    @ApiModelProperty("订单id")
    private String orderId;
    /**
     * 额外费用说明
     */     
    @ApiModelProperty("额外费用说明")
    private String instruction;
    /**
     * 币种
     */     
    @ApiModelProperty("币种")
    private String currency;
    /**
     * 金额
     */     
    @ApiModelProperty("金额")
    private BigDecimal price;
    /**
     * 创建人
     */     
    @ApiModelProperty("创建人")
    private String createBy;
    /**
     * 创建时间
     */     
    @ApiModelProperty("创建时间")
    private Date createTime;
    /**
     * 更新人
     */     
    @ApiModelProperty("更新人")
    private String updateBy;
    /**
     * 更新时间
     */     
    @ApiModelProperty("更新时间")
    private Date updateTime;
    /**
     * 逻辑删除 0否 1是
     */     
    @ApiModelProperty("逻辑删除 0否 1是")
    private Integer izDelete;

    @ApiModelProperty("客户订单号")
    private String customerOrderNum;

    @ApiModelProperty("汇率")
    private BigDecimal exchangeRate;

    @ApiModelProperty("订单产品id")
    private String orderDetailId;
}

