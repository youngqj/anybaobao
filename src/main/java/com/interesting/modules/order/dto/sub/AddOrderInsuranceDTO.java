package com.interesting.modules.order.dto.sub;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 保险费用信息
 */
@Data
public class AddOrderInsuranceDTO {
    /**主键*/
    @ApiModelProperty(value = "主键")
    private String id;
    /**货物保险公司*/
    @ApiModelProperty(value = "货物保险公司")
    private String insuranceCompany;
    /**货物保险费*/
    @ApiModelProperty(value = "货物保险费")
    private java.math.BigDecimal insuranceFee;
    /**备注*/
    @ApiModelProperty(value = "备注")
    private String remark;
    /**申请日期*/
    @ApiModelProperty(value = "申请日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.util.Date applicationDate;
    /**财务确认金额*/
    @ApiModelProperty(value = "财务确认金额")
    private java.math.BigDecimal financeConfirmAmount;
    /**财务审核日期*/
    @ApiModelProperty(value = "财务审核日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.util.Date financeAuditDate;
}
