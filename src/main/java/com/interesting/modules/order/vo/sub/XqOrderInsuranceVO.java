package com.interesting.modules.order.vo.sub;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class XqOrderInsuranceVO {
    /**主键*/
    @ApiModelProperty(value = "主键")
    private String id;
    /**货物保险公司*/
    @ApiModelProperty(value = "货物保险公司")
    private String insuranceCompany;
    /**货物保险费*/
    @ApiModelProperty(value = "货物保险费")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal insuranceFee;
    /**备注*/
    @ApiModelProperty(value = "备注")
    private String remark;
    /**申请日期*/
    @ApiModelProperty(value = "申请日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private java.util.Date applicationDate;
    /**财务确认金额*/
    @ApiModelProperty(value = "财务确认金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal financeConfirmAmount;
    /**财务审核日期*/
    @ApiModelProperty(value = "财务审核日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private java.util.Date financeAuditDate;
}
