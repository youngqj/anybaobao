package com.interesting.modules.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/9/14 13:54
 * @VERSION: V1.0
 */
@Data
public class ListXqOrderFinallyConfirmVO {

    @ApiModelProperty("主键")
    private String id;
    /**
     * 订单id
     */
    @ApiModelProperty("订单id")
    private String orderId;
    /**
     * 确认人id
     */
    @ApiModelProperty("确认人id")
    private String userId;
    /**
     * （字典）角色名
     */
    @ApiModelProperty("（字典）角色名")
    private String roleId;

    @ApiModelProperty("确认情况")
    private Integer izConfirmed;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;


}
