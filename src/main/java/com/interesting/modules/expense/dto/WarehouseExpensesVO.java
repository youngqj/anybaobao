package com.interesting.modules.expense.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/3/11 16:39
 * @Project: trade-manage
 * @Description:
 */

@Data
public class WarehouseExpensesVO implements Serializable {

    private static final long SerialVersionUID = 1L;

    @ApiModelProperty(value = "主键 id")
    private  String id;

    @ApiModelProperty(value = "仓库ID")
    private String warehouseId;

    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "发票号")
    private String invoiceNum;

    @ApiModelProperty(value = "费用USD")
    private BigDecimal expense;

    @ApiModelProperty(value = "发票日期")
    private String invoiceDate;

    @ApiModelProperty(value = "费用名称")
    private String remark;

    @ApiModelProperty(value = "状态：0 未结清  1 结清")
    private String izSettle;

    @ApiModelProperty(value = "付款金额")
    private BigDecimal paymentAmount;

    @ApiModelProperty(value = "备注")
    private String remark2;

    @ApiModelProperty(value = "财务审核金额")
    private BigDecimal auditAmount;

    @ApiModelProperty(value = "财务审核日期")
    private String auditDate;

    @ApiModelProperty(value = "到期付款日")
    private String dueDate;
    @ApiModelProperty(value = "申请时间")
    private String applyForDate;

    @ApiModelProperty(value = "导入时间")
    private String createTime;

    @ApiModelProperty(value = "实际付款日")
    private String paymentDate;

    @ApiModelProperty(value = "失败提示语",hidden = true)
    private String failMsg;


}
