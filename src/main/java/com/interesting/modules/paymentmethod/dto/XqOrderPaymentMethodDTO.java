package com.interesting.modules.paymentmethod.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/9/15 14:25
 * @VERSION: V1.0
 */
@Data
public class XqOrderPaymentMethodDTO {
    /**
     * 主键
     */
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


}
