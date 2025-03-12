package com.interesting.modules.overseas.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RelativeOrderPageVO {
    /**主键*/
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    /**主键*/
    @ApiModelProperty(value = "客户名称")
    private String customerName;

    /**订单号*/
    @ApiModelProperty(value = "订单号")
    private String orderNum;

    /**合计金额*/
    @ApiModelProperty(value = "合计金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal totalMoney;

    /**产品信息*/
    @ApiModelProperty(value = "产品信息")
    private String productInfo;

    /**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**创建时间*/
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;

    @ApiModelProperty("入库完成状态(字典)")
    private String enterState;

    @ApiModelProperty("出库完成状态(字典)")
    private String exitState;

    @ApiModelProperty("入库完成状态")
    private String enterStateTxt;

    @ApiModelProperty("出库完成状态")
    private String exitStateTxt;
}
