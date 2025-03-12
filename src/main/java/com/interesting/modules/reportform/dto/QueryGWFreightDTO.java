package com.interesting.modules.reportform.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryGWFreightDTO {
    private String column = "create_time";

    private String order = "desc";

    private String reportFormTimeOrder;


    @ApiModelProperty("自定义列")
    private String exportColumn;

    @ApiModelProperty(value = "装船开始日期")
    private String etdStartDate;

    @ApiModelProperty(value = "装船结束日期")
    private String etdEndDate;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "PO号")
    private String orderNum;

    @ApiModelProperty(value = "船公司")
    private String shipCompany;

    @ApiModelProperty(value = "国内海运公司")
    private String gnhyfAgent;

    @ApiModelProperty(value = "国内港杂公司")
    private String gngzfAgent;

    @ApiModelProperty(value = "国外清关行")
    private String gwqgfAgent;

    @ApiModelProperty(value = "国外卡车公司")
    private String gwkcfAgent;

    @ApiModelProperty(value = "国外杂费对应公司1")
    private String ewfy1Agent;

    @ApiModelProperty(value = "国外杂费对应公司2")
    private String ewfy2Agent;

    @ApiModelProperty(value = "国外杂费对应公司3")
    private String ewfy3Agent;

    @ApiModelProperty(value = "国外杂费对应公司4")
    private String ewfy4Agent;


    @ApiModelProperty(value = "国外杂费对应公司5")
    private String ewfy5Agent;


    @ApiModelProperty(value = "国外杂费对应公司6")
    private String ewfy6Agent;


    @ApiModelProperty(value = "国外查验公司")
    private String gwcyfAgent;


    @ApiModelProperty(value = "付款开始日期")
    private String payTimeStartDate;

    @ApiModelProperty(value = "付款结束日期")
    private String payTimeStartEnd;

}
