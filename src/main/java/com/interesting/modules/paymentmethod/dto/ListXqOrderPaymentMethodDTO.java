package com.interesting.modules.paymentmethod.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/9/15 14:24
 * @VERSION: V1.0
 */
@Data
public class ListXqOrderPaymentMethodDTO {

    @ApiModelProperty("付款方式")
    private String text;



}
