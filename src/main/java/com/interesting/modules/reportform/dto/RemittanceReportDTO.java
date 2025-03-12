package com.interesting.modules.reportform.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/9/19 9:36
 * @VERSION: V1.0
 */
@Data
public class RemittanceReportDTO {
    private String column = "create_time";

    private String order = "desc";

    private String reportFormTimeOrder;


    @ApiModelProperty("自定义列")
    private String exportColumn;

    /**收汇日期*/
    @ApiModelProperty(value = "收汇日期开始")
    private String remittanceDateStart;
    /**收汇日期*/
    @ApiModelProperty(value = "收汇日期结束")
    private String remittanceDateEnd;


    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "发票号")
    private String invoiceNum;

    @ApiModelProperty(value = "订单号")
    private String orderNum;

}
