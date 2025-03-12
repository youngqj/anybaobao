package com.interesting.modules.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/9/14 18:21
 * @VERSION: V1.0
 */
@Data
public class TransferDTO {

    @ApiModelProperty(value = "订单id",required = true)
    @NotBlank(message = "订单不可为空")
    private String orderId;

    @ApiModelProperty(value = "转让目标",required = true)
    @NotBlank(message = "转让目标不可为空")
    private String transferTarget;



}
