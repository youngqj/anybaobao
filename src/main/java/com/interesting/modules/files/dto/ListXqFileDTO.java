package com.interesting.modules.files.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ListXqFileDTO {
    @ApiModelProperty(value = "订单id", required = true)
    @NotBlank(message = "订单id不能为空")
    private String orderId;

    @ApiModelProperty(value = "模块类型", required = true)
    @NotBlank(message = "模块类型不能为空")
    private String moduleType;
}
