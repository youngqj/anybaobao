package com.interesting.modules.paymentmethod.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/9/15 14:21
 * @VERSION: V1.0
 */
@Data
public class ListXqOrderPaymentMethodVO {
    @ApiModelProperty("主键")
    private String id;
    /**
     * 付款方式显示的值
     */
    @ApiModelProperty("付款方式显示的值")
    private String text;
    /**
     * 实际天数
     */
    @ApiModelProperty("实际天数")
    private Integer value;

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

}
