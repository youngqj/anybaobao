package com.interesting.modules.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuditDTO {
    @ApiModelProperty(value = "主键，多个以逗号分割", required = true)
    @NotBlank(message = "主键不能为空")
    private String ids;

    @ApiModelProperty(value = "审核状态", required = true)
    @NotBlank(message = "审核状态不能为空")
    private String auditStatus;
}
