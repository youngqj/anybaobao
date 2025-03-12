package com.interesting.modules.prepayments.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class XqPrepaymentsQueryDto {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "预付款id")
    private String paymentDetailIds;

    @ApiModelProperty(value = "收款工厂id")
    private String supplierIds;

    @ApiModelProperty(value = "付款公司id")
    private String paymentCompanyIds;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "付款开始日期")
    private Date paymentStartDate;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "付款结束日期")
    private Date paymentEndDate;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否显示剩余金额大于0的数据（1-显示 0不显示）")
    private Integer isShow0 = 1;

    @ApiModelProperty(value = "排序字段")
    private String column;
    @ApiModelProperty(value = "排序方式(asc-正序 desc-倒序)")
    private String order;
}
