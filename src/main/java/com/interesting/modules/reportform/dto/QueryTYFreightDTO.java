package com.interesting.modules.reportform.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2024/1/23 16:31
 * @VERSION: V1.0
 */
@Data
public class QueryTYFreightDTO {
    private String column = "create_time";

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


    @ApiModelProperty(value = "装船开始日期")
    private String etdStartDate;

    @ApiModelProperty(value = "装船结束日期")
    private String etdEndDate;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "PO号")
    private String orderNum;

    @ApiModelProperty("中文名")
    private String productName;

    @ApiModelProperty(value = "船公司")
    private String shipCompany;

    @ApiModelProperty("装货港")
    private String loadingPort;

    @ApiModelProperty("卸货港")
    private String destinationPort;

    @ApiModelProperty("提单号")
    private String billOfLading;

    @ApiModelProperty("箱号")
    private String containerNo;




}
