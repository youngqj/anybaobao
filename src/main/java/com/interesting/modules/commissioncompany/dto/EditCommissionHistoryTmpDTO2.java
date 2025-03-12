package com.interesting.modules.commissioncompany.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EditCommissionHistoryTmpDTO2 {
    @ApiModelProperty(value = "模板id", required = true)
    @NotBlank(message = "模板id不能为空")
    private String id;

    @ApiModelProperty("模板名称")
    @NotBlank(message = "模板名称不能为空")
    private String name;
}
