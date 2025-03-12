package com.interesting.modules.commissioncompany.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddCommissionHistoryTmpDTO {
    @ApiModelProperty(value = "模板id", required = true)
    @NotBlank(message = "模板id不能为空")
    private String templateId;

    /**佣金公司*/
    @ApiModelProperty(value = "佣金公司", required = true)
    @NotBlank(message = "佣金公司不能为空")
    private String companyName;

    /**佣金条件*/
    @ApiModelProperty(value = "佣金条件（字典）", required = true)
    @NotBlank(message = "佣金条件不能为空")
    private String requirements;

    /**佣金比例*/
    @ApiModelProperty(value = "佣金比例", required = true)
    @NotNull(message = "佣金比例不能为空")
    private java.math.BigDecimal percentage;
}
