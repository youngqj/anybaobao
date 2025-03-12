package com.interesting.modules.reportform.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryFreightKCDTO {
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    @ApiModelProperty("自定义列")
    private String exportColumn;

    @ApiModelProperty(value = "PO号")
    private String orderNum;

    @ApiModelProperty(value = "卡车")
    private String truck;

    @ApiModelProperty("申请日期开始")
    private String applyDateStart;

    @ApiModelProperty("申请日期结束")
    private String applyDateEnd;

    @ApiModelProperty("付款日期开始")
    private String payDateStart;

    @ApiModelProperty("付款日期结束")
    private String payDateEnd;



}
