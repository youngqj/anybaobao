package com.interesting.modules.expense.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/3/13 17:36
 * @Project: trade-manage
 * @Description:
 */

@Data
public class QueryExpensesDTO {
    private Integer pageNo = 1;

    private Integer pageSize = 10;

//    private String column = "create_time";

    private String order = "desc";

    private String column;


    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "仓库名称")
    private List<String> warehouseNames;

    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "发票号")
    private String invoiceNum;

    @ApiModelProperty(value = "发票开始日期 2024-03-13")
    private String invoiceDateStart;
    @ApiModelProperty(value = "发票结束日期 2024-03-13")
    private String invoiceDateEnd;
    @ApiModelProperty(value = "状态：0 未结清  1 结清")
    private Integer izSettle;

    @ApiModelProperty(value = "到期付款日开始 2024-03-13")
    private String dueDateStart;
    @ApiModelProperty(value = "到期付款日结束 2024-03-13")
    private String dueDateEnd;
    @ApiModelProperty(value = "实际付款日开始 2024-03-13")
    private String paymentDateStart;
    @ApiModelProperty(value = "实际付款日结束 2024-03-13")
    private String paymentDateEnd;


    @ApiModelProperty(value = "申请时间开始 2024-03-13")
    private String applyForDateStart;
    @ApiModelProperty(value = "申请时间结束 2024-03-13")
    private String applyForDateEnd;

    @ApiModelProperty(value = "导入时间开始 2024-03-13")
    private String createTimeStart;
    @ApiModelProperty(value = "导入时间结束 2024-03-13")
    private String createTimeEnd;

    @ApiModelProperty(value = "费用名称")
    private String remark;

}
