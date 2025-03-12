package com.interesting.modules.order.dto.sub;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;

@Data
public class AddOrderComsDTO {
    @ApiModelProperty("id：新增时不用传")
    private String id;

    /**佣金公司*/
    @ApiModelProperty(value = "佣金公司", required = true)
    @NotBlank(message = "佣金公司不能为空")
    private String companyName;

    /**客户订单号*/
    @ApiModelProperty(value = "客户订单号")
    private String customerOrderNum;

    /**佣金条件*/
    @ApiModelProperty(value = "佣金条件", required = true)
    private String requirements;

    /**佣金比例*/
    @ApiModelProperty(value = "佣金比例", required = true)
    private java.math.BigDecimal percentage;

    /**佣金金额*/
    @ApiModelProperty(value = "佣金金额")
    private java.math.BigDecimal amount;

    /**申请日期*/
    @ApiModelProperty(value = "申请日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.util.Date applicationDate;

    /**财务确认金额*/
    @ApiModelProperty(value = "财务确认金额")
    private java.math.BigDecimal financeConfirmedAmount;

    /**财务审核日期*/
    @ApiModelProperty(value = "财务审核日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.util.Date financeAuditDate;

    /**备注*/
    @ApiModelProperty(value = "备注")
    private String notes;

    @ApiModelProperty("佣金类型（字典）")
    private String commissionType;

    /**佣金参数*/
    @ApiModelProperty(value = "佣金参数")
    private java.math.BigDecimal param;


    /**
     * 佣金比例
     */
    @ApiModelProperty(value = "佣金比例JSON")
    private String commissionDetailsStr;
}
