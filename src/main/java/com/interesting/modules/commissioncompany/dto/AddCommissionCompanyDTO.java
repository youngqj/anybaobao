package com.interesting.modules.commissioncompany.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class AddCommissionCompanyDTO {
    @ApiModelProperty("编辑时传id")
    private String id;

//    @ApiModelProperty(value = "客户id", required = true)
//    @NotBlank(message = "客户id不能为空")
//    private String customerId;

    @ApiModelProperty(value = "佣金公司名称", required = true)
    @NotBlank(message = "佣金公司名称不能为空")
    private String name;

    @ApiModelProperty(value = "佣金公司条件", required = true)
    @NotBlank(message = "佣金公司条件不能为空")
    private String requirements;

    @ApiModelProperty(value = "佣金比例", required = true)
    @NotNull(message = "佣金比例不能为空")
    private BigDecimal percentage;
}
