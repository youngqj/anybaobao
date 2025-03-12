package com.interesting.modules.overseas.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class QueryPageSurplusOrderDTO {
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    @ApiModelProperty(value = "订单id", required = true)
    @NotBlank(message = "订单id不能为空")
    private String orderId;

    @ApiModelProperty(value = "产品名称")
    private String productName;
}
