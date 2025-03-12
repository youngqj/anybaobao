package com.interesting.modules.reportform.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/3/14 14:44
 * @Project: trade-manage
 * @Description:
 */


@Data
public class QueryTiHuoLiRunDTO {


    @ApiModelProperty(value = "提货开始时间 2023-03-14")
    private String bolTimeStart;
    @ApiModelProperty(value = "提货结束时间 2023-03-14")
    private String bolTimeEnd;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "初始来源订单号")
    private String sourceRepNum;

    @ApiModelProperty(value = "客户订单号")
    private String customerOrderNum;

    @ApiModelProperty(value = "仓库id",hidden = true)
    private String warehouseId;

    @ApiModelProperty(value = "仓库lot")
    private String warehouseLot;

    @ApiModelProperty(value = "发票号",hidden = true)
    private String invoiceNum;



}
