package com.interesting.modules.reportform.dto;

import com.interesting.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @create 2021-08-04 16:06
 *
 */
@Data
public class QueryTYFreightNewDTO {
    private String column = "return_cargo_time";

    private String order = "desc";

    private String reportFormTimeOrder;


    @ApiModelProperty("自定义列")
    private String exportColumn;

    @ApiModelProperty("申请日期开始")
    private String applicationDateStart;

    @ApiModelProperty("申请日期结束")
    private String applicationDateEnd;

    @ApiModelProperty("付款日期开始")
    private String payDateStart;

    @ApiModelProperty("付款日期结束")
    private String payDateEnd;

    @ApiModelProperty(value = "PO号")
    private String orderNum;

    @ApiModelProperty(value = "提单号码")
    private String returnBillOfLading;

    @ApiModelProperty(value = "装运港")
    private String returnLoadingPort;

    @ApiModelProperty(value = "目的港")
    private String returnDestinationPort;

    @ApiModelProperty(value = "费用类型")
    private String feeType;




}
