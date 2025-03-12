package com.interesting.modules.reportform.dto;

import com.interesting.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryFinanceDTO {
    private String column = "create_time";

    private String order = "desc";

    private String reportFormTimeOrder;

    @ApiModelProperty("自定义列")
    private String exportColumn;

    @ApiModelProperty(value = "装船开始日期")
    private String etdStartDate;

    @ApiModelProperty(value = "装船结束日期")
    private String etdEndDate;

    @ApiModelProperty(value = "客户id，可多选")
    private String customerName;

    @ApiModelProperty(value = "PO号")
    private String orderNum;

    @ApiModelProperty(value = "采购币种")
    private String currency;

    @ApiModelProperty(value = "销售币种")
    private String salesCurrency;


    @ApiModelProperty(value = "发票号")
    private String invoiceNum;

    @ApiModelProperty(value = "中文名")
    private String productName;

    @ApiModelProperty(value = "国外收货人")
    private String overseasReceiver;

    @ApiModelProperty(value = "国内发货人")
    private String domesticSender;

    @ApiModelProperty(value = "供应商")
    private String supplierName;

    @ApiModelProperty(value = "开票资料")
    private String kpzl;

    @ApiModelProperty(value = "进项开票日期开始")
    private String jxkprqStart;

    @ApiModelProperty(value = "进项开票日期结束")
    private String jxkprqEnd;

    @ApiModelProperty(value = "收汇日期开始")
    private String shrqStart;

    @ApiModelProperty(value = "收汇日期结束")
    private String shrqEnd;

    @ApiModelProperty(value = "出货方式")
    private String chfs;


}
