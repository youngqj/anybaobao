package com.interesting.modules.customer.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CommissionCompanyVO {
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "佣金公司名称")
    private String name;

    @ApiModelProperty(value = "佣金公司条件")
    private String requirements;

    @ApiModelProperty(value = "佣金比例")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal percentage;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
