package com.interesting.modules.commissioncompany.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddCommissionHistoryTmpDTO2 {
    @ApiModelProperty(value = "佣金模板名称", required = true)
    @NotBlank(message = "佣金模板名称不能为空")
    private String name;

    @ApiModelProperty(value = "客户id", required = true)
    @NotBlank(message = "客户id不能为空")
    private String customerId;
}
