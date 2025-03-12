package com.interesting.modules.commission.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommissionHistoryTmpDetailsVO {
    /**主键*/
    @ApiModelProperty(value = "主键")
    private String id;

    /**佣金公司*/
    @ApiModelProperty(value = "佣金公司")
    private String companyName;

    /**佣金条件*/
    @ApiModelProperty(value = "佣金条件（字典）")
    private String requirements;

    /**佣金条件*/
    @ApiModelProperty(value = "佣金条件（文本）")
    private String requirementsTxt;

    /**佣金比例*/
    @ApiModelProperty(value = "佣金比例")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal percentage;
}
