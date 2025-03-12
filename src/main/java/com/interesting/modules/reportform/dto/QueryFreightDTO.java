package com.interesting.modules.reportform.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryFreightDTO {
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

    @ApiModelProperty(value = "国内陆运费公司")
    private String gnlyfAgent;

    @ApiModelProperty(value = "国内报关公司")
    private String gnbgfAgent;

    @ApiModelProperty(value = "海运保险公司")
    private String gnhyfAgent;

    @ApiModelProperty(value = "国内舱单费公司")
    private String gncdfAgent;

    @ApiModelProperty(value = "送货费公司")
    private String shfAgent;


    @ApiModelProperty(value = "付款日期开始")
    private String payTimeStartDate;

    @ApiModelProperty(value = "付款结束日期")
    private String payTimeStartEnd;

    @ApiModelProperty(value = "国内报关申请日期开始")
    private String gnlyfApplicationTimeStart;

    @ApiModelProperty(value = "国内报关申请日期结束")
    private String gnlyfApplicationTimeEnd;


    @ApiModelProperty(value = "国内报关申请日期开始")
    private String gnbgfApplicationTimeStart;

    @ApiModelProperty(value = "国内报关申请日期结束")
    private String gnbgfApplicationTimeEnd;

    @ApiModelProperty(value = "国内海运申请日期开始")
    private String gnhyfApplicationTimeStart;

    @ApiModelProperty(value = "国内海运申请日期结束")
    private String gnhyfApplicationTimeEnd;

    @ApiModelProperty(value = "国内舱单费申请日期开始")
    private String gncdfApplicationTimeStart;

    @ApiModelProperty(value = "国内舱单费申请日期结束")
    private String gncdfApplicationTimeEnd;

    @ApiModelProperty(value = "送货费申请日期开始")
    private String shfApplicationTimeStart;

    @ApiModelProperty(value = "送货费申请日期结束")
    private String shfApplicationTimeEnd;


}
