package com.interesting.modules.overseas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Accessors(chain = true)
public class QueryPageExitStorageDetailDTO {
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    private String order = "desc";

    private String column;

    private String exportColumn;

    @ApiModelProperty(value = "bol")
    private String bol;

    @ApiModelProperty(value = "bol时间开始")
    private String bolTimeBegin;

    @ApiModelProperty(value = "bol时间结束")
    private String bolTimeEnd;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "客户订单号")
    private String customerOrderNum;

    @ApiModelProperty(value = "初始来源订单号")
    private String sourceRepNum;

    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "仓库lot")
    private String warehouseLot;

    @ApiModelProperty(value = "发票号")
    private String invoiceNum;

    @ApiModelProperty(value = "产品名称")
    private String productName;


    @ApiModelProperty(value = "收汇情况1-未收汇，2-部分收汇，3-全额收汇")
    private String remmitStatus;

    @ApiModelProperty(value = "投保开始日期 2024-03-20")
    private String insuranceEffectiveDateStart;
    @ApiModelProperty(value = "投保结束日期 2024-03-20")
    private String insuranceEffectiveDateEnd;
}
